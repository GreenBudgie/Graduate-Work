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
import ru.sut.graduate.entity.Schema
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.service.SchemaService
import ru.sut.graduate.service.StageService
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.ui.component.*

@Route(value = "transitions", layout = MainLayout::class)
@PageTitle("УП | Переходы")
class TransitionView(
    private val transitionService: TransitionService,
    private val stageService: StageService,
    private val schemaService: SchemaService
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
        fromStageDropdown.width = "33%"

        val toStageDropdown = EntityDropdown(Stage::name, stageService)
        toStageDropdown.placeholder = "В состояние"
        toStageDropdown.width = "33%"

        val schemaDropdown = EntityDropdown(Schema::name, schemaService)
        schemaDropdown.placeholder = "Схема данных"
        schemaDropdown.width = "33%"

        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "40%"

        val addTransitionButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addTransitionButton.width = "30%"
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
                schema = schemaDropdown.entity
            )
            transitionService.saveOnUI(transition)
            nameInput.clear()
            fromStageDropdown.clear()
            toStageDropdown.clear()
            schemaDropdown.clear()
            grid.loadItems()
        }

        val topLayout = HorizontalLayout(fromStageDropdown, toStageDropdown, schemaDropdown)
        topLayout.setWidthFull()
        topLayout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        val bottomLayout = HorizontalLayout(nameInput, addTransitionButton)
        bottomLayout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        add(VerticalLayout(topLayout, bottomLayout))
    }

    private fun addGrid() {
        grid.addEditableColumn(Transition::name)
            .setHeader("Наименование")
            .setFlexGrow(0)
            .setWidth("25%")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::fromStage,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("Из состояния")
            .setWidth("25%")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::toStage,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("В состояние")
            .setWidth("25%")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::schema,
            EntityDropdown(Schema::name, schemaService)
        )
            .setHeader("Схема данных")
            .setWidth("25%")
            .isSortable = false
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}