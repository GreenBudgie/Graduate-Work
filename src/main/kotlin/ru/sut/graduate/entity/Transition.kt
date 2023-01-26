package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Transition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var fromStage: Stage? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var toStage: Stage? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    var parameters: Set<ProcessParameter> = emptySet()
) : GenericEntity()