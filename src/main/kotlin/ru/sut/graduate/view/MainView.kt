package ru.sut.graduate.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.Process
import ru.sut.graduate.repository.ProcessRepository

@Route
class MainView(
    private val processRepository: ProcessRepository
) : VerticalLayout() {

    private val grid = Grid(Process::class.java)

    init {
        add(grid)
        grid.setItems(Process(1))
        grid.addComponentColumn {
            val button = Button()
            button.icon = Icon(VaadinIcon.TRASH)
            button.setTooltipText("Delete " + it.id.toString())
            button
        }.setHeader("delete")
    }

}