package ru.sut.graduate.ui.component

import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.data.renderer.ComponentRenderer
import ru.sut.graduate.vo.LocalizableEnum
import kotlin.reflect.KClass

class EnumDropdown<T>(
    enumClass: KClass<T>
) : ComboBox<EnumDropdown.DropdownEntry<T>>() where T : Enum<*>, T : LocalizableEnum {

    init {
        val values = enumClass.java.enumConstants
            .map { DropdownEntry(it) }
            .sortedBy { it.enumConstant?.getLocalizedName() }
        val removeRow = DropdownEntry<T>(null)
        setItems(listOf(removeRow) + values)
        val renderer = ComponentRenderer(::getRowComponent)
        setRenderer(renderer)
        setItemLabelGenerator { it.enumConstant?.getLocalizedName() ?: "" }
        addValueChangeListener {
            if(it.value?.enumConstant as Any? == null) {
                it.source.value = it.source.emptyValue
            }
        }
    }

    private fun getRowComponent(entry: DropdownEntry<T>): HorizontalLayout {
        val name = entry.enumConstant?.getLocalizedName()
        return if(name != null) {
            HorizontalLayout(Span(name))
        } else {
            val icon = Icon(VaadinIcon.ERASER)
            icon.setSize("20px")
            HorizontalLayout(
                icon,
                Span("Очистить")
            )
        }
    }

    data class DropdownEntry<T> (
        var enumConstant: T?
    ) where T : Enum<*>, T : LocalizableEnum

}