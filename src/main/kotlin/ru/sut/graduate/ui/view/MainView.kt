package ru.sut.graduate.ui.view

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "", layout = MainLayout::class)
@PageTitle("Главная страница")
class MainView : VerticalLayout() {

    init {
        add(
            VerticalLayout(
                routeButton("Список систем", "authSystems"),
                routeButton("Добавить систему", "addAuthSystem"),
                routeButton("Найти систему", "findAuthSystem"),
                routeButton("О программе", "about")
            )
        )
    }

    private fun routeButton(text: String, route: String): Button {
        val button = Button(text)
        button.addClickListener {
            UI.getCurrent().navigate(route)
        }
        return button
    }

}