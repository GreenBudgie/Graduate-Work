package ru.sut.graduate.ui.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.ProcessParameter
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.service.ProcessParameterService
import ru.sut.graduate.service.StageService
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.ui.component.*

@Route(value = "transitions", layout = MainLayout::class)
@PageTitle("УП | Переходы")
class TransitionView(
    private val transitionService: TransitionService,
    private val stageService: StageService,
    private val processParameterService: ProcessParameterService
) : VerticalLayout() {

    private val grid = EntityGrid(Transition::class, transitionService)

    init {
        addForm()
        addGrid()
        this.setHeightFull()
    }

    private fun addForm() {
        val fromStageDropdown = EntityDropdown(Stage::name, stageService)
        fromStageDropdown.placeholder = "Из состояния"
        fromStageDropdown.width = "30%"

        val toStageDropdown = EntityDropdown(Stage::name, stageService)
        toStageDropdown.placeholder = "В состояние"
        toStageDropdown.width = "30%"

        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "40%"

        val parametersDropdown = EntityMultiDropdown(ProcessParameter::name, processParameterService)
        parametersDropdown.placeholder = "Параметры заявки"
        parametersDropdown.width = "60%"

        val addTransitionButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addTransitionButton.width = "40%"
        addTransitionButton.minWidth = "200px"

        addTransitionButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование перехода")
                return@addClickListener
            }
            if(toStageDropdown.isEmpty) {
                ClosableNotification.error("Укажите конечное состояние")
                return@addClickListener
            }
            val transition = Transition(
                name = nameInput.value,
                fromStage = fromStageDropdown.entity,
                toStage = toStageDropdown.entity,
                parameters = parametersDropdown.value
            )
            transitionService.saveOnUI(transition)
            nameInput.clear()
            fromStageDropdown.clear()
            toStageDropdown.clear()
            parametersDropdown.clear()
            grid.loadItems()
        }

        val topLayout = HorizontalLayout(fromStageDropdown, toStageDropdown, nameInput)
        topLayout.setWidthFull()
        topLayout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        val bottomLayout = HorizontalLayout(parametersDropdown, addTransitionButton)
        bottomLayout.setWidthFull()
        bottomLayout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        add(VerticalLayout(topLayout, bottomLayout))
    }

    private fun addGrid() {
        grid.addEditableColumn(Transition::name)
            .setHeader("Наименование")
            .setFlexGrow(0)
            .setWidth("20%")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::fromStage,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("Из состояния")
            .setWidth("15%")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::toStage,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("В состояние")
            .setWidth("15%")
            .isSortable = true
        grid.addMultiDropdownEditableColumn(
            Transition::parameters,
            EntityMultiDropdown(ProcessParameter::name, processParameterService)
        )
            .setHeader("Параметры")
            .setAutoWidth(true)
            .isSortable = false
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}