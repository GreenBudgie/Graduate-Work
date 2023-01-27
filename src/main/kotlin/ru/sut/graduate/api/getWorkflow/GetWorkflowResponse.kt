package ru.sut.graduate.api.getWorkflow

data class GetWorkflowResponse(
    val id: Long,
    val name: String,
    val stages: List<StageData>,
    val transitions: List<TransitionData>
)

data class StageData(
    val id: Long,
    val name: String
)

data class TransitionData(
    val id: Long,
    val name: String,
    val fromStageId: Long?,
    val toStageId: Long,
    val schemaId: Long?
)