package ru.sut.graduate.ui.view

import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "about", layout = MainLayout::class)
@PageTitle("О программе")
class AboutView : VerticalLayout() {

    init {
        add(
            VerticalLayout(
                Paragraph("СПбГУТ, Киселева Дарья ИСТ-941, 2023"),
                Paragraph("kiselevadasha6258@gmail.com"),
                Paragraph("Благодарю за содействие Бухарина В.В.")
            )
        )
    }

}