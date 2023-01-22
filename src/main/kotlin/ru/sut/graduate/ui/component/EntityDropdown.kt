package ru.sut.graduate.ui.component

import com.vaadin.flow.component.combobox.ComboBox
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.service.GenericService
import kotlin.reflect.KProperty1

class EntityDropdown<T : GenericEntity>(
    displayField: KProperty1<T, Any?>,
    valueProvider: () -> List<T>
) : ComboBox<T>() {

    constructor(
        displayField: KProperty1<T, Any?>,
        service: GenericService<T, *>
    ) : this(displayField, service::findAll)

    init {
        setItems(valueProvider())
        setItemLabelGenerator {
            displayField.get(it).toString()
        }
    }

}