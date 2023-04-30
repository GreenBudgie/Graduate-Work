package ru.sut.graduate.ui.view

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.Software
import ru.sut.graduate.service.SoftwareService
import ru.sut.graduate.ui.component.EntityGrid
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "", layout = MainLayout::class)
@PageTitle("Список ПО")
class SoftwareView(
    private val softwareService: SoftwareService
) : VerticalLayout() {

    private val grid = EntityGrid(Software::class, softwareService)

    init {
        grid.addStringEditableColumn(Software::name)
            .setHeader("Наименование")
            .isSortable = true
        grid.addIntEditableColumn(Software::segments)
            .setHeader("Кол-во сегментов")
            .isSortable = true
        grid.addIntEditableColumn(Software::hosts)
            .setHeader("Кол-во хостов в сегменте")
            .isSortable = true
        grid.addMultiEnumEditableColumn(Software::supportedOS)
            .setHeader("Поддерживаемые ОС")
            .isSortable = true
        grid.addEnumEditableColumn(Software::authType)
            .setHeader("Тип аутентификации")
            .isSortable = true
        grid.addIntEditableColumn(Software::trustFactor)
            .setHeader("Уровень доверия")
            .isSortable = true
        grid.addIntEditableColumn(Software::keyLength)
            .setHeader("Длина ключа")
            .isSortable = true
        grid.addMultiEnumEditableColumn(Software::supportedBrowsers)
            .setHeader("Поддерживаемые браузеры")
            .isSortable = true
        grid.addIntEditableColumn(Software::keyLength)
            .setHeader("Длина ключа")
            .isSortable = true
        grid.addBooleanEditableColumn(Software::supportsMobile)
            .setHeader("Поддержка мобильных устройств")
            .isSortable = true
        grid.addBooleanEditableColumn(Software::supportsDocker)
            .setHeader("Поддержка Docker")
            .isSortable = true
        grid.addIntEditableColumn(Software::price)
            .setHeader("Цена")
            .isSortable = true
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}