package ru.sut.graduate.ui.component

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.combobox.MultiSelectComboBox
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.service.GenericService
import kotlin.reflect.KProperty1

abstract class DropdownBase<T : GenericEntity>(
    val displayFieldGetter: (T) -> Any?,
    valueProvider: () -> List<T>
) : MultiSelectComboBox<T>() {

    constructor(
        displayField: KProperty1<T, Any?>,
        valueProvider: () -> List<T>
    ) : this(displayField.getter, valueProvider)

    constructor(
        displayField: KProperty1<T, Any?>,
        service: GenericService<T, *>
    ) : this(displayField, service::findAll)

    final override fun setItems(items: Collection<T>?): ComboBoxListDataView<T> {
        return super.setItems(items)
    }

    final override fun setItemLabelGenerator(itemLabelGenerator: ItemLabelGenerator<T>) {
        super.setItemLabelGenerator(itemLabelGenerator)
    }

    init {
        setItems(valueProvider())
        setItemLabelGenerator {
            displayFieldGetter(it).toString()
        }
    }

}