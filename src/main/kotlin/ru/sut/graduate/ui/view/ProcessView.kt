package ru.sut.graduate.ui.view

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.Process
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "processes", layout = MainLayout::class)
@PageTitle("УП | Заявки")
class ProcessView : VerticalLayout() {

    private val grid = Grid(Process::class.java)

    init {
        grid.setItems(Process())
        add(grid)
    }

}