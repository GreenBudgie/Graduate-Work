package ru.sut.graduate.ui.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.service.StageService
import ru.sut.graduate.ui.component.EntityGrid
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "", layout = MainLayout::class)
@PageTitle("Состояния")
class StageView(
    private val stageService: StageService
) : VerticalLayout() {

    init {
        val grid = EntityGrid(Stage::class, stageService)
        grid.addEditableColumn(Stage::name, true)
            .setHeader("Наименование")
            .setFlexGrow(0)
            .setAutoWidth(true)
            .isSortable = true
        grid.addEditableColumn(Stage::description)
            .setHeader("Описание")
            .isSortable = true
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)

        val addStageText = H2("Добавить состояние")
        val nameInput = TextField("Наименование")
        nameInput.isRequired = true
        val descriptionInput = TextField("Описание")
        val addStageButton = Button("Добавить")
        addStageButton.addClickListener {
            stageService.save(Stage(name = nameInput.value, description = descriptionInput.value))
            grid.loadItems()
        }
        add(addStageText, nameInput, descriptionInput, addStageButton)

    }

}