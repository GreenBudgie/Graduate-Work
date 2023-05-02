package ru.sut.graduate.ui.component

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import ru.sut.graduate.ui.view.AddAuthSystemView
import ru.sut.graduate.ui.view.FindAuthSystemView
import ru.sut.graduate.ui.view.AuthSystemView

class MainLayout : AppLayout() {

    private val tabs = Tabs()

    init {
        val toggle = DrawerToggle()

        val title = H1("Системы аутентификации")
        title.style
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "var(--lumo-space-m)")

        tabs.add(Tab(RouterLink("Список систем", AuthSystemView::class.java)))
        tabs.add(Tab(RouterLink("Добавить систему", AddAuthSystemView::class.java)))
        tabs.add(Tab(RouterLink("Найти систему", FindAuthSystemView::class.java)))
        tabs.orientation = Tabs.Orientation.VERTICAL

        addToNavbar(toggle, title)
        addToDrawer(tabs)
    }

}