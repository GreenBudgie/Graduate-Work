package ru.sut.graduate.ui.component

import com.vaadin.flow.component.combobox.MultiSelectComboBox
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.service.GenericService
import kotlin.reflect.KProperty1

class EntityMultiDropdown<T : GenericEntity>(
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

    init {
        setItems(valueProvider())
        setItemLabelGenerator {
            displayFieldGetter(it).toString()
        }
    }

}