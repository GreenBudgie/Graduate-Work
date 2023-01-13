package ru.sut.graduate.view

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import ru.sut.graduate.domain.Process
import ru.sut.graduate.repository.ProcessRepository

@Route
class MainView(
    private val processRepository: ProcessRepository
) : VerticalLayout() {

    private val grid = Grid(Process::class.java)

    init {
        add(grid)
        grid.setItems(processRepository.findAll())
    }

}