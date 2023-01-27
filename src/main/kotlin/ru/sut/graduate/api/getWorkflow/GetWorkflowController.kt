package ru.sut.graduate.api.getWorkflow

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.entity.Workflow
import ru.sut.graduate.service.WorkflowService

@RestController
@RequestMapping("api/getWorkflow")
class GetWorkflowController(
    private val workflowService: WorkflowService
) {

    @GetMapping
    fun execute(): GetWorkflowResponse {
        val workflows = workflowService.findAll()
        val workflowsData = workflows.map { convertToWorkflowData(it) }
        return GetWorkflowResponse(workflowsData)
    }

    private fun convertToWorkflowData(workflow: Workflow): WorkflowData {
        val transitions = workflow.transitions
        val stages = transitions
            .flatMap { listOf(it.fromStage, it.toStage) }
            .filterNotNull()
            .distinctBy { it.id }
        val stagesData = stages.map { convertToStageData(it) }
        val transitionsData = transitions.map { convertToTransitionData(it) }
        return WorkflowData(
            workflow.id!!,
            workflow.name!!,
            stagesData,
            transitionsData
        )
    }

    private fun convertToStageData(stage: Stage): StageData {
        return StageData(stage.id!!, stage.name!!)
    }

    private fun convertToTransitionData(transition: Transition): TransitionData {
        return TransitionData(
            transition.id!!,
            transition.name!!,
            transition.fromStage?.id,
            transition.toStage!!.id!!
        )
    }

}