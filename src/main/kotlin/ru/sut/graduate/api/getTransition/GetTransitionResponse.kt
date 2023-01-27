package ru.sut.graduate.api.getTransition

data class GetTransitionResponse(
    val id: Long,
    val name: String,
    val fromStageId: Long?,
    val toStageId: Long,
    val schemaId: Long?
)