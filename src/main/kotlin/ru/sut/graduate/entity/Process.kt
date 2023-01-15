package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Process(
    id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    val stage: Stage? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    val workflow: Workflow? = null,
) : GenericEntity(id)