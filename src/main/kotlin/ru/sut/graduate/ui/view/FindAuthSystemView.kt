package ru.sut.graduate.ui.view

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.AuthSystem
import ru.sut.graduate.service.AuthSystemService
import ru.sut.graduate.ui.component.*
import ru.sut.graduate.vo.AuthType
import ru.sut.graduate.vo.BooleanEnum
import ru.sut.graduate.vo.Browser
import ru.sut.graduate.vo.OS

@Route(value = "findAuthSystem", layout = MainLayout::class)
@PageTitle("Найти систему аутентификации")
class FindAuthSystemView(
    private val authSystemService: AuthSystemService
) : VerticalLayout() {

    private val resultGrid = EntityGrid(AuthSystem::class, authSystemService).apply { isVisible = false }
    private val noResultsLabel = Label("Система аутентификации с заданными параметрами не найдена").apply { isVisible = false }

    init {
        resultGrid.addColumn(AuthSystem::name)
            .setHeader("Наименование")
            .isSortable = true
        resultGrid.addColumn(AuthSystem::price)
            .setHeader("Цена")
            .isSortable = true
        resultGrid.width = "50%"

        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "50%"

        val segmentsInput = IntegerField()
        segmentsInput.placeholder = "Количество сегментов"
        segmentsInput.width = "25%"
        segmentsInput.min = 0

        val hostsInput = IntegerField()
        hostsInput.placeholder = "Количество хостов в сегменте"
        hostsInput.width = "25%"
        hostsInput.min = 0

        val supportedOSDropdown = EnumMultiDropdown(OS::class)
        supportedOSDropdown.placeholder = "Поддерживаемые ОС"
        supportedOSDropdown.width = "50%"

        val authTypeDropdown = EnumDropdown(AuthType::class)
        authTypeDropdown.placeholder = "Тип аутентификации"
        authTypeDropdown.width = "25%"

        val trustFactorInput = IntegerField()
        trustFactorInput.placeholder = "Уровень доверия"
        trustFactorInput.width = "25%"
        trustFactorInput.min = 0
        trustFactorInput.max = 3

        val keyLengthInput = IntegerField()
        keyLengthInput.placeholder = "Длина ключа"
        keyLengthInput.width = "25%"
        keyLengthInput.min = 0

        val supportedBrowsersDropdown = EnumMultiDropdown(Browser::class)
        supportedBrowsersDropdown.placeholder = "Поддерживаемые браузеры"
        supportedBrowsersDropdown.width = "50%"

        val supportsMobileDropdown = EnumDropdown(BooleanEnum::class)
        supportsMobileDropdown.placeholder = "Поддержка мобильных устройств"
        supportsMobileDropdown.width = "50%"

        val supportsDockerDropdown = EnumDropdown(BooleanEnum::class)
        supportsDockerDropdown.placeholder = "Поддержка Docker"
        supportsDockerDropdown.width = "50%"

        val priceInput = IntegerField()
        priceInput.placeholder = "Цена"
        priceInput.suffixComponent = Paragraph("\u20BD")
        priceInput.width = "25%"
        priceInput.min = 0

        val findSoftwareButton = Button("Найти", Icon(VaadinIcon.SEARCH))
        findSoftwareButton.width = "20%"
        findSoftwareButton.minWidth = "200px"
        findSoftwareButton.addClickListener {
            val authSystem = AuthSystem(
                name = nameInput.optionalValue.orElse(null),
                segments = segmentsInput.value,
                hosts = hostsInput.value,
                supportedOS = supportedOSDropdown.value,
                authType = authTypeDropdown.value?.enumConstant,
                trustFactor = trustFactorInput.value,
                keyLength = keyLengthInput.value,
                supportedBrowsers = supportedBrowsersDropdown.value,
                supportsMobile = supportsMobileDropdown.value?.enumConstant?.toBoolean(),
                supportsDocker = supportsDockerDropdown.value?.enumConstant?.toBoolean(),
                price = priceInput.value
            )
            val results = authSystemService.findByExampleSoftware(authSystem)
            if(results.isEmpty()) {
                showNoResults()
            } else {
                showResultGrid(results)
            }
        }

        val clearButton = Button("Очистить", Icon(VaadinIcon.ERASER))
        clearButton.width = "20%"
        clearButton.minWidth = "200px"
        clearButton.addClickListener {
            resultGrid.isVisible = false
            noResultsLabel.isVisible = false
            nameInput.clear()
            segmentsInput.clear()
            hostsInput.clear()
            supportedOSDropdown.clear()
            authTypeDropdown.clear()
            trustFactorInput.clear()
            keyLengthInput.clear()
            supportedBrowsersDropdown.clear()
            supportsMobileDropdown.clear()
            supportsDockerDropdown.clear()
            priceInput.clear()
        }


        add(
            VerticalLayout(
                layout(nameInput, priceInput).apply { width = "80%" },
                layout(supportedOSDropdown, supportedBrowsersDropdown),
                layout(supportsMobileDropdown, supportsDockerDropdown).apply { width = "80%" },
                layout(segmentsInput, hostsInput),
                layout(trustFactorInput, keyLengthInput),
                HorizontalLayout(findSoftwareButton, clearButton),
                resultGrid,
                noResultsLabel
            )
        )
    }

    private fun showResultGrid(authSystemList: List<AuthSystem>) {
        resultGrid.isVisible = true
        noResultsLabel.isVisible = false
        resultGrid.setItems(authSystemList)
        resultGrid.recalculateColumnWidths()
    }

    private fun showNoResults() {
        resultGrid.isVisible = false
        noResultsLabel.isVisible = true
    }

    private fun layout(vararg components: Component): HorizontalLayout {
        val layout = HorizontalLayout(*components)
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.START
        return layout
    }

}