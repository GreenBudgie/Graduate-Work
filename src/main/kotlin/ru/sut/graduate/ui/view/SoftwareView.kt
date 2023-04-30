package ru.sut.graduate.ui.view

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.service.SoftwareService
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "", layout = MainLayout::class)
@PageTitle("Программное обеспечение")
class SoftwareView(
    private val softwareService: SoftwareService
) : VerticalLayout() {

}