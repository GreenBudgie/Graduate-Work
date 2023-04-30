package ru.sut.graduate.ui.view

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
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
import ru.sut.graduate.entity.Software
import ru.sut.graduate.service.SoftwareService
import ru.sut.graduate.ui.component.ClosableNotification
import ru.sut.graduate.ui.component.EnumDropdown
import ru.sut.graduate.ui.component.EnumMultiDropdown
import ru.sut.graduate.ui.component.MainLayout
import ru.sut.graduate.vo.AuthType
import ru.sut.graduate.vo.BooleanEnum
import ru.sut.graduate.vo.Browser
import ru.sut.graduate.vo.OS

@Route(value = "addSoftware", layout = MainLayout::class)
@PageTitle("Добавить ПО")
class AddSoftwareView(
    private val softwareService: SoftwareService
) : VerticalLayout() {

    init {
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
        supportedOSDropdown.width = "40%"

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
        supportedBrowsersDropdown.width = "40%"

        val supportsMobileDropdown = EnumDropdown(BooleanEnum::class)
        supportsMobileDropdown.placeholder = "Поддержка мобильных устройств"
        supportsMobileDropdown.width = "40%"

        val supportsDockerDropdown = EnumDropdown(BooleanEnum::class)
        supportsDockerDropdown.placeholder = "Поддержка Docker"
        supportsDockerDropdown.width = "40%"

        val priceInput = IntegerField()
        priceInput.placeholder = "Цена"
        priceInput.suffixComponent = Paragraph("\u20BD")
        priceInput.width = "25%"
        priceInput.min = 0

        val addSoftwareButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addSoftwareButton.width = "20%"
        addSoftwareButton.minWidth = "200px"
        addSoftwareButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование ПО")
                return@addClickListener
            }
            val software = Software(
                name = nameInput.value,
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
            softwareService.saveOnUI(software)
            nameInput.clear()
        }

        add(
            VerticalLayout(
                layout(nameInput, priceInput).apply { width = "75%" },
                layout(supportedOSDropdown, supportedBrowsersDropdown).apply { width = "80%" },
                layout(supportsMobileDropdown, supportsDockerDropdown).apply { width = "80%" },
                layout(segmentsInput, hostsInput, trustFactorInput, keyLengthInput),
                HorizontalLayout(addSoftwareButton),
            )
        )
    }

    private fun layout(vararg components: Component): HorizontalLayout {
        val layout = HorizontalLayout(*components)
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.START
        return layout
    }

}