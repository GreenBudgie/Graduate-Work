package ru.sut.graduate.api.startNewProcess

data class StartNewProcessRequest(
    val workflowId: Long,
    val name: String,
    val initialTransitionId: Long,
    val processContext: Map<String, String>
)