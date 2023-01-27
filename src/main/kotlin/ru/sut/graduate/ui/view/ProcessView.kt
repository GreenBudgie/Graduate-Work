package ru.sut.graduate.ui.view

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.Process
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.entity.Workflow
import ru.sut.graduate.service.ProcessService
import ru.sut.graduate.service.StageService
import ru.sut.graduate.service.WorkflowService
import ru.sut.graduate.ui.component.EntityDropdown
import ru.sut.graduate.ui.component.EntityGrid
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "processes", layout = MainLayout::class)
@PageTitle("УП | Заявки")
class ProcessView(
    private val processService: ProcessService,
    private val stageService: StageService,
    private val workflowService: WorkflowService
) : VerticalLayout() {

    private val grid = EntityGrid(Process::class, processService)

    init {
        addGrid()
        this.setHeightFull()
    }

    private fun addGrid() {
        grid.addDropdownEditableColumn(
            Process::stage,
            EntityDropdown(Stage::name, stageService)
        )
            .setHeader("Состояние")
            .isSortable = true
        grid.addDropdownEditableColumn(
            Process::workflow,
            EntityDropdown(Workflow::name, workflowService)
        )
            .setHeader("Маршрут")
            .isSortable = true
        add(grid)
    }

}