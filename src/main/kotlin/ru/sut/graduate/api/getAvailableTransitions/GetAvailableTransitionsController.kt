package ru.sut.graduate.api.getAvailableTransitions

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.entity.Workflow
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.service.WorkflowService

@RestController
@RequestMapping("api/getAvailableTransitions")
class GetAvailableTransitionsController(
    private val transitionService: TransitionService,
    private val workflowService: WorkflowService
) {

    @GetMapping
    fun execute(): GetAvailableTransitionsResponse {
        val workflows = workflowService.findAll()
        val workflowsData = workflows.map { convertToWorkflowData(it) }
        return GetAvailableTransitionsResponse(workflowsData)
    }

    private fun convertToWorkflowData(workflow: Workflow): WorkflowData {
        val transitions = workflow.transitions
        val transitionsForCreation = transitionService.findForCreation(transitions)
        val transitionsData = transitionsForCreation.map { convertToTransitionData(it) }
        return WorkflowData(
            workflow.id!!,
            workflow.name!!,
            transitionsData
        )
    }

    private fun convertToTransitionData(transition: Transition): TransitionData {
        return TransitionData(
            transition.id!!,
            transition.name!!,
            transition.toStage!!.id!!,
            transition.schema?.id
        )
    }

}