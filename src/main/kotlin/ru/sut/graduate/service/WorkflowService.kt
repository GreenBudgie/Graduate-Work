package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.entity.Workflow
import ru.sut.graduate.repository.WorkflowRepository

@Service
class WorkflowService : GenericService<Workflow, WorkflowRepository>() {

    override fun validate(entity: Workflow) {
        validateFieldUniqueness(
            entity,
            Workflow::name,
            "Маршрут с таким наименованием уже существует"
        )
    }

    fun containsTransition(workflow: Workflow, transition: Transition): Boolean {
        return workflow.transitions.contains(transition)
    }

}