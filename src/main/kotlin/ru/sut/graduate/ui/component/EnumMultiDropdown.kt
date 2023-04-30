package ru.sut.graduate.ui.component

import com.vaadin.flow.component.combobox.MultiSelectComboBox
import ru.sut.graduate.vo.LocalizableEnum
import kotlin.reflect.KClass

class EnumMultiDropdown<T>(
    enumClass: KClass<T>
) : MultiSelectComboBox<T>() where T : Enum<*>, T : LocalizableEnum {

    init {
        val values = enumClass.java.enumConstants.sortedBy { it.getLocalizedName() }
        setItems(values)
        setItemLabelGenerator {
            it.getLocalizedName()
        }
    }

}