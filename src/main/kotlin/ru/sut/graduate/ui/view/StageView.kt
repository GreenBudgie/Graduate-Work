package ru.sut.graduate.ui.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.sut.graduate.ui.component.ClosableNotification
import ru.sut.graduate.ui.component.EntityGrid
import ru.sut.graduate.ui.component.MainLayout

@Route(value = "", layout = MainLayout::class)
@PageTitle("УП | Состояния")
class StageView(
    private val stageService: StageService
) : VerticalLayout() {

    private val grid = EntityGrid(Stage::class, stageService)

    init {
        addForm()
        addGrid()
        this.setHeightFull()
    }

    private fun addForm() {
        val nameInput = TextField()
        nameInput.placeholder = "Наименование"
        nameInput.width = "80%"
        val addStageButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addStageButton.width = "20%"
        addStageButton.minWidth = "200px"
        addStageButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование состояния")
                return@addClickListener
            }
            val stage = Stage(name = nameInput.value)
            stageService.saveOnUI(stage)
            nameInput.clear()
            grid.loadItems()
        }
        val layout = HorizontalLayout(nameInput, addStageButton)
        layout.width = "70%"
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
        add(layout)
    }

    private fun addGrid() {
        grid.addEditableColumn(Stage::name)
            .setHeader("Наименование")
            .setAutoWidth(true)
            .isSortable = true
        grid.addEditAction()
        grid.addDeleteAction()
        grid.loadItems()
        add(grid)
    }

}