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
import ru.sut.graduate.entity.Schema
import ru.sut.graduate.service.ProcessParameterService
import ru.sut.graduate.service.SchemaService
import ru.sut.graduate.ui.component.*

@Route(value = "schemas", layout = MainLayout::class)
@PageTitle("УП | Схемы данных")
class SchemaView(
    private val schemaService: SchemaService,
    private val processParameterService: ProcessParameterService
) : VerticalLayout() {

    private val grid = EntityGrid(Schema::class, schemaService)

    init {
        addForm()
        addGrid()
        this.setHeightFull()
    }

    private fun addForm() {
        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "35%"

        val parametersDropdown = EntityMultiDropdown(ProcessParameter::name, processParameterService)
        parametersDropdown.placeholder = "Параметры заявки"
        parametersDropdown.width = "50%"

        val addSchemaButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addSchemaButton.width = "15%"
        addSchemaButton.minWidth = "200px"

        addSchemaButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование схемы данных")
                return@addClickListener
            }
            val schema = Schema(
                name = nameInput.value,
                parameters = parametersDropdown.value
            )
            schemaService.saveOnUI(schema)
            nameInput.clear()
            parametersDropdown.clear()
            grid.loadItems()
        }

        val layout = HorizontalLayout(nameInput, parametersDropdown, addSchemaButton)
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        add(layout)
    }

    private fun addGrid() {
        grid.addEditableColumn(Schema::name)
            .setHeader("Наименование")
            .setFlexGrow(0)
            .setWidth("25%")
            .isSortable = true
        grid.addMultiDropdownEditableColumn(
            Schema::parameters,
            EntityMultiDropdown(ProcessParameter::name, processParameterService)
        )
            .setHeader("Параметры заявки")
            .setAutoWidth(true)
            .isSortable = false
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}