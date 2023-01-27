package ru.sut.graduate.service.process

import ru.sut.graduate.entity.Transition
import ru.sut.graduate.entity.Workflow

data class NewProcessContext(
    val name: String,
    val workflow: Workflow,
    val initialTransition: Transition,
    val processContext: Map<String, String>
)