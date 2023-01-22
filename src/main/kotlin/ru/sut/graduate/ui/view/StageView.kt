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
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.service.StageService
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
        nameInput.width = "35%"
        val descriptionInput = TextField()
        descriptionInput.placeholder = "Описание"
        descriptionInput.width = "50%"
        val addStageButton = Button("Добавить", Icon(VaadinIcon.PLUS))
        addStageButton.width = "15%"
        addStageButton.minWidth = "200px"
        addStageButton.addClickListener {
            if(nameInput.value.isBlank()) {
                ClosableNotification.error("Укажите наименование состояния")
                return@addClickListener
            }
            val stage = Stage(
                name = nameInput.value,
                description = descriptionInput.value
            )
            if(!stageService.saveOnUI(stage)) return@addClickListener
            nameInput.clear()
            descriptionInput.clear()
            grid.loadItems()
        }
        val layout = HorizontalLayout(nameInput, descriptionInput, addStageButton)
        layout.setWidthFull()
        layout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
        add(layout)
    }

    private fun addGrid() {
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
    }

}