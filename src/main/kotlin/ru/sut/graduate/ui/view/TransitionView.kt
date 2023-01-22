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
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.service.StageService
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.ui.component.ClosableNotification
import ru.sut.graduate.ui.component.EntityDropdown
import ru.sut.graduate.ui.component.EntityGrid
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "transitions", layout = MainLayout::class)
@PageTitle("УП | Переходы")
class TransitionView(
    private val transitionService: TransitionService,
    private val stageService: StageService
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
        fromStageDropdown.width = "25%"
        val toStageDropdown = EntityDropdown(Stage::name, stageService)
        toStageDropdown.placeholder = "В состояние"
        toStageDropdown.width = "25%"
        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "35%"
        val addTransitionButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addTransitionButton.width = "15%"
        addTransitionButton.minWidth = "200px"
        addTransitionButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование перехода")
                return@addClickListener
            }
            if(fromStageDropdown.isEmpty) {
                ClosableNotification.error("Укажите начальное состояние")
                return@addClickListener
            }
            if(toStageDropdown.isEmpty) {
                ClosableNotification.error("Укажите конечное состояние")
                return@addClickListener
            }
            val transition = Transition(
                name = nameInput.value,
                fromStage = fromStageDropdown.value,
                toStage = toStageDropdown.value
            )
            transitionService.saveOnUI(transition) ?: return@addClickListener
            nameInput.clear()
            fromStageDropdown.clear()
            toStageDropdown.clear()
            grid.loadItems()
        }
        val layout = HorizontalLayout(fromStageDropdown, toStageDropdown, nameInput, addTransitionButton)
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
        add(layout)
    }

    private fun addGrid() {
        grid.addEditableColumn(Transition::name, true)
            .setHeader("Наименование")
            .setFlexGrow(0)
            .setAutoWidth(true)
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::fromStage,
            Transition::fromStageName,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("Из состояния")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Transition::toStage,
            Transition::toStageName,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("В состояние")
            .isSortable = true
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}