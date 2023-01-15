package ru.sut.graduate.ui.component

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.provider.SortDirection
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.service.GenericService
import java.lang.IllegalStateException
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

class EntityGrid<T : GenericEntity>(
    private val entityClass: KClass<T>,
    private val service: GenericService<T, *>
) : Grid<T>() {

    private var deleteEnabled = false
    private var editEnabled = false

    init {
        editor.binder = Binder(entityClass.java)
        addIDColumn()
        editor.addSaveListener {
            service.save(it.item)
        }
    }

    fun addEditableColumn(property: KMutableProperty1<T, String?>,
                          required: Boolean = false): Column<T> {
        val field = TextField()
        field.setWidthFull()
        val fieldBinder = editor.binder.forField(field)
        if(required) {
            fieldBinder.asRequired()
        }
        fieldBinder.bind(property.getter, property.setter)

        val column = addColumn(property.getter)
        column.editorComponent = field
        return column
    }


    fun addDeleteAction(): Column<T> {
        if(deleteEnabled) {
            throw IllegalStateException("Delete action is already enabled")
        }
        deleteEnabled = true
        val deleteColumn = addComponentColumn { entity ->
            val button = Button()
            button.setTooltipText("Удалить")
            button.icon = Icon(VaadinIcon.TRASH)
            button.themeName = ButtonVariant.LUMO_ICON.variantName
            button.addClickListener {
                service.delete(entity)
                loadItems()
            }
            button
        }
        deleteColumn.isFrozenToEnd = true
        deleteColumn.isAutoWidth = true
        deleteColumn.flexGrow = 0

        return deleteColumn
    }

    fun addEditAction(): Column<T> {
        if(editEnabled) {
            throw IllegalStateException("Edit action is already enabled")
        }
        editor.isBuffered = true
        editEnabled = true

        val editColumn = addComponentColumn { entity ->
            val editButton = Button()
            editButton.setTooltipText("Редактировать")
            editButton.icon = Icon(VaadinIcon.PENCIL)
            editButton.themeName = ButtonVariant.LUMO_ICON.variantName
            editButton.width = "100px"
            editButton.addClickListener {
                if(editor.isOpen) {
                    editor.cancel()
                }
                editor.editItem(entity)
                recalculateColumnWidths()
            }
            editButton
        }
        editColumn.isFrozenToEnd = true
        editColumn.isAutoWidth = true
        editColumn.flexGrow = 0

        val saveButton = Button()
        saveButton.icon = Icon(VaadinIcon.ADD_DOCK)
        saveButton.themeName = ButtonVariant.LUMO_ICON.variantName
        saveButton.setTooltipText("Сохранить")
        saveButton.addClickListener {
            editor.save()
            recalculateColumnWidths()
        }

        val cancelButton = Button()
        cancelButton.icon = Icon(VaadinIcon.CLOSE)
        cancelButton.themeName = ButtonVariant.LUMO_ICON.variantName
        cancelButton.setTooltipText("Отменить")
        cancelButton.addClickListener { editor.cancel() }

        val editorLayout = HorizontalLayout(saveButton, cancelButton)
        editorLayout.alignItems = FlexComponent.Alignment.CENTER
        editorLayout.justifyContentMode = FlexComponent.JustifyContentMode.EVENLY
        editColumn.editorComponent = editorLayout
        return editColumn
    }

    fun loadItems() {
        setItems(service.findAll())
        recalculateColumnWidths()
    }

    private fun addIDColumn() {
        val idColumn = addColumn(GenericEntity::id)
        idColumn.setHeader("ID")
        idColumn.flexGrow = 0
        idColumn.isAutoWidth = true
        idColumn.isSortable = true
        this.sort(listOf(
            GridSortOrder(idColumn, SortDirection.ASCENDING)
        ))
    }

}