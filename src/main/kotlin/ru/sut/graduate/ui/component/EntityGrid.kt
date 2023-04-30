package ru.sut.graduate.ui.component

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.function.SerializableFunction
import com.vaadin.flow.function.ValueProvider
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.service.GenericService
import ru.sut.graduate.vo.BooleanEnum
import ru.sut.graduate.vo.LocalizableEnum
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

class EntityGrid<T : GenericEntity>(
    private val entityClass: KClass<T>,
    private val service: GenericService<T, *>
) : Grid<T>() {

    private val maxTextLengthWithNoTooltip = 25

    private var deleteEnabled = false
    private var editEnabled = false

    init {
        editor.binder = Binder(entityClass.java)
        editor.addSaveListener {
            service.saveOnUI(it.item)
            loadItems()
        }
    }

    inline fun <reified E> addEnumEditableColumn(
        property: KMutableProperty1<T, E?>
    ) : Column<T> where E : Enum<*>, E : LocalizableEnum {
        val dropdown = EnumDropdown(E::class)
        dropdown.setWidthFull()
        val fieldBinder = editor.binder.forField(dropdown)
        fieldBinder.bind(
            { EnumDropdown.DropdownEntry(property.get(it)) },
            { entity, entry -> property.set(entity, entry?.enumConstant) }
        )

        val column = addColumn {
            property.get(it)?.getLocalizedName() ?: ""
        }
        column.editorComponent = dropdown
        return column
    }

    inline fun <reified E> addMultiEnumEditableColumn(
        property: KMutableProperty1<T, Set<E>>
    ) : Column<T> where E : Enum<*>, E : LocalizableEnum {
        val dropdown = EnumMultiDropdown(E::class)
        dropdown.setWidthFull()
        val fieldBinder = editor.binder.forField(dropdown)
        fieldBinder.bind(property.getter, property.setter)

        val column = addColumn {
            val enumConstants = property.get(it)
            val displayProperties = enumConstants.map(LocalizableEnum::getLocalizedName)
            displayProperties.joinToString(", ")
        }
        column.editorComponent = dropdown
        return column
    }

    fun addStringEditableColumn(property: KMutableProperty1<T, String?>): Column<T> {
        val field = TextField()
        field.setWidthFull()
        val fieldBinder = editor.binder.forField(field)
        fieldBinder.bind(property.getter, property.setter)

        val column = addColumn(property.getter)
        column.editorComponent = field
        return column
    }

    fun addIntEditableColumn(property: KMutableProperty1<T, Int?>): Column<T> {
        val field = TextField()
        field.setWidthFull()
        val fieldBinder = editor.binder.forField(field)
        fieldBinder.bind(
            { property.get(it).toString() },
            { _, value -> value.toInt() }
        )

        val column = addColumn(property.getter)
        column.editorComponent = field
        return column
    }

    fun addBooleanEditableColumn(property: KMutableProperty1<T, Boolean?>): Column<T> {
        val dropdown = EnumDropdown(BooleanEnum::class)
        dropdown.setWidthFull()
        val fieldBinder = editor.binder.forField(dropdown)
        fieldBinder.bind(
            {
                val booleanValue = property.get(it) ?: return@bind null
                EnumDropdown.DropdownEntry(BooleanEnum.from(booleanValue))
            },
            { entity, entry -> property.set(entity, entry?.enumConstant?.toBoolean()) }
        )

        val column = addColumn {
            val booleanValue = property.get(it) ?: return@addColumn ""
            BooleanEnum.from(booleanValue)
        }
        column.editorComponent = dropdown
        return column
    }

    override fun addColumn(valueProvider: ValueProvider<T, *>): Column<T> {
        val column = super.addColumn(valueProvider)
        column.tooltipGenerator = SerializableFunction {
            val text = valueProvider.apply(it)?.toString() ?: ""
            if(text.length < maxTextLengthWithNoTooltip) {
                return@SerializableFunction null
            }
            return@SerializableFunction text
        }
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
                service.deleteOnUI(entity)
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
            loadItems()
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

}