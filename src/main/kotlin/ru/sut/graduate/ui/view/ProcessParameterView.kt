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
import ru.sut.graduate.service.ProcessParameterService
import ru.sut.graduate.ui.component.ClosableNotification
import ru.sut.graduate.ui.component.EntityGrid
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "processParameters", layout = MainLayout::class)
@PageTitle("УП | Параметры заявок")
class ProcessParameterView(
    private val processParameterService: ProcessParameterService
) : VerticalLayout() {

    private val grid = EntityGrid(ProcessParameter::class, processParameterService)

    init {
        addForm()
        addGrid()
        this.setHeightFull()
    }

    private fun addForm() {
        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "80%"
        val addParameterButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addParameterButton.width = "20%"
        addParameterButton.minWidth = "200px"
        addParameterButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование параметра заявки")
                return@addClickListener
            }
            val processParameter = ProcessParameter(name = nameInput.value)
            processParameterService.saveOnUI(processParameter)
            nameInput.clear()
            grid.loadItems()
        }
        val layout = HorizontalLayout(nameInput, addParameterButton)
        layout.width = "70%"
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
        add(layout)
    }

    private fun addGrid() {
        grid.addEditableColumn(ProcessParameter::name)
            .setHeader("Наименование")
            .isSortable = true
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}