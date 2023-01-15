package ru.sut.graduate.view

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.Stage

@Route(value = "", layout = MainLayout::class)
@PageTitle("Состояния")
class StageView : VerticalLayout() {

    private val grid = Grid(Stage::class.java)

    init {
        grid.setItems(Stage(1, name = "Name", description = "Desc"))
        add(grid)
    }

}