package ru.sut.graduate.api.moveToStage

data class MoveToStageRequest(
    val processId: Long,
    val transitionId: Long,
    val processContext: Map<String, String>
)