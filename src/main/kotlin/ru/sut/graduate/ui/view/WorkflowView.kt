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
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.entity.Workflow
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.service.WorkflowService
import ru.sut.graduate.ui.component.*

@Route(value = "workflows", layout = MainLayout::class)
@PageTitle("УП | Маршруты")
class WorkflowView(
    private val workflowService: WorkflowService,
    private val transitionService: TransitionService,
) : VerticalLayout() {

    private val grid = EntityGrid(Workflow::class, workflowService)

    init {
        addForm()
        addGrid()
        this.setHeightFull()
    }

    private fun addForm() {
        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "35%"

        val transitionsDropdown = EntityMultiDropdown(Transition::name, transitionService)
        transitionsDropdown.placeholder = "Доступные переходы"
        transitionsDropdown.width = "50%"

        val addWorkflowButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addWorkflowButton.width = "15%"
        addWorkflowButton.minWidth = "200px"

        addWorkflowButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование маршрута")
                return@addClickListener
            }
            val workflow = Workflow(
                name = nameInput.value,
                transitions = transitionsDropdown.value
            )
            workflowService.saveOnUI(workflow)
            nameInput.clear()
            transitionsDropdown.clear()
            grid.loadItems()
        }

        val layout = HorizontalLayout(nameInput, transitionsDropdown, addWorkflowButton)
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        add(layout)
    }

    private fun addGrid() {
        grid.addEditableColumn(Workflow::name)
            .setHeader("Наименование")
            .setFlexGrow(0)
            .setWidth("25%")
            .isSortable = true
        grid.addMultiDropdownEditableColumn(
            Workflow::transitions,
            EntityMultiDropdown(Transition::name, transitionService)
        )
            .setHeader("Доступные переходы")
            .setAutoWidth(true)
            .isSortable = false
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}