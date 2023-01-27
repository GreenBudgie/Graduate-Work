package ru.sut.graduate.api.moveToStage

class MoveToStageResponse(
    val id: Long,
    val name: String,
    val workflowId: Long,
    val stageId: Long
)