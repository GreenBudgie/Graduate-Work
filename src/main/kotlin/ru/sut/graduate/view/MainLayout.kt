package ru.sut.graduate.view

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.RouterLink


class MainLayout : AppLayout() {

    private val tabs = Tabs()

    init {
        val toggle = DrawerToggle()

        val title = H1("Система управления потоками")
        title.style
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "var(--lumo-space-m)")

        tabs.add(Tab(RouterLink("Состояния", StageView::class.java)))
        tabs.add(Tab(RouterLink("Переходы", StageView::class.java)))
        tabs.add(Tab(RouterLink("Маршруты", StageView::class.java)))
        tabs.add(Tab(RouterLink("Заявки", ProcessView::class.java)))
        tabs.orientation = Tabs.Orientation.VERTICAL

        addToNavbar(toggle, title)
        addToDrawer(tabs)
    }

}