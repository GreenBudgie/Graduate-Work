package ru.sut.graduate.api.getAvailableTransitions

data class GetAvailableTransitionsResponse(
    val workflows: List<WorkflowData>
)

data class WorkflowData(
    val id: Long,
    val name: String,
    val availableTransitions: List<TransitionData>
)

data class TransitionData(
    val id: Long,
    val name: String,
    val toStageId: Long,
    val schemaId: Long?
)