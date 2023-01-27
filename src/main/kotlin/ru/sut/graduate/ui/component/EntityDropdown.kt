package ru.sut.graduate.ui.component

import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.data.renderer.ComponentRenderer
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.service.GenericService
import kotlin.reflect.KProperty1

class EntityDropdown<T : GenericEntity>(
    val displayFieldGetter: (T) -> Any?,
    private val values: List<DropdownEntry<T>>
) : ComboBox<EntityDropdown.DropdownEntry<T>>() {

    val entity get() = value?.entity

    constructor(
        displayField: KProperty1<T, Any?>,
        valueProvider: () -> List<T>
    ) : this(displayField.getter, valueProvider().map { DropdownEntry(it) })

    constructor(
        displayField: KProperty1<T, Any?>,
        service: GenericService<T, *>
    ) : this(displayField, service::findAll)

    init {
        val removeRow = DropdownEntry<T>(null)
        setItems(listOf(removeRow) + values)
        val renderer = ComponentRenderer(::getRowComponent)
        setRenderer(renderer)
        setItemLabelGenerator {
            val entity = it.entity ?: return@setItemLabelGenerator ""
            displayFieldGetter(entity).toString()
        }
        addValueChangeListener {
            if(it.value?.entity == null) {
                it.source.value = it.source.emptyValue
            }
        }
    }

    private fun getRowComponent(entry: DropdownEntry<T>): HorizontalLayout {
        val entity = entry.entity
        return if(entity != null) {
            HorizontalLayout(
                Span(displayFieldGetter(entity).toString())
            )
        } else {
            val icon = Icon(VaadinIcon.ERASER)
            icon.setSize("20px")
            HorizontalLayout(
                icon,
                Span("Очистить")
            )
        }
    }

    data class DropdownEntry<T : GenericEntity>(
        var entity: T?
    )

}