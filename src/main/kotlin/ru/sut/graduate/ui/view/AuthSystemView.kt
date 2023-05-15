package ru.sut.graduate.ui.view

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.AuthSystem
import ru.sut.graduate.service.AuthSystemService
import ru.sut.graduate.ui.component.EntityGrid

@Route(value = "authSystems", layout = MainLayout::class)
@PageTitle("Список систем аутентификации")
class AuthSystemView(
    private val authSystemService: AuthSystemService
) : VerticalLayout() {

    private val grid = EntityGrid(AuthSystem::class, authSystemService)

    init {
        grid.addStringEditableColumn(AuthSystem::name)
            .setHeader("Наименование")
            .isSortable = true
        grid.addIntEditableColumn(AuthSystem::segments)
            .setHeader("Кол-во доменов")
            .isSortable = true
        grid.addIntEditableColumn(AuthSystem::hosts)
            .setHeader("Кол-во пользователей")
            .isSortable = true
        grid.addMultiEnumEditableColumn(AuthSystem::supportedOS)
            .setHeader("Поддерживаемые ОС")
            .isSortable = true
        grid.addEnumEditableColumn(AuthSystem::authType)
            .setHeader("Тип аутентификации")
            .isSortable = true
        grid.addIntEditableColumn(AuthSystem::trustFactor)
            .setHeader("Уровень доверия")
            .isSortable = true
        grid.addIntEditableColumn(AuthSystem::keyLength)
            .setHeader("Длина ключа")
            .isSortable = true
        grid.addMultiEnumEditableColumn(AuthSystem::supportedBrowsers)
            .setHeader("Поддерживаемые браузеры")
            .isSortable = true
        grid.addBooleanEditableColumn(AuthSystem::supportsMobile)
            .setHeader("Поддержка мобильных устройств")
            .isSortable = true
        grid.addBooleanEditableColumn(AuthSystem::supportsDocker)
            .setHeader("Поддержка Docker")
            .isSortable = true
        grid.addIntEditableColumn(AuthSystem::price)
            .setHeader("Цена")
            .isSortable = true
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        grid.width = "300%"
        add(grid)
    }

}