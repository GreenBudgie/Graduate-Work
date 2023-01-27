package ru.sut.graduate.api.startNewProcess

class StartNewProcessResponse(
    val id: Long,
    val name: String,
    val workflowId: Long,
    val stageId: Long
)