package ru.sut.graduate.service.process

import ru.sut.graduate.entity.ProcessParameterValue
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.entity.Workflow

data class NewProcessContext(
    val name: String,
    val workflow: Workflow,
    val initialTransition: Transition,
    val processContext: List<ProcessParameterValue>
)