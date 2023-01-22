package ru.sut.graduate.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.Transient

@Entity
class Transition(
    id: Long? = null,
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var fromStage: Stage? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var toStage: Stage? = null,
) : GenericEntity(id) {

    @get:Transient
    val fromStageName get() = fromStage?.name

    @get:Transient
    val toStageName get() = toStage?.name

}