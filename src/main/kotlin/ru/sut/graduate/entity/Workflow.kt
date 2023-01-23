package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Workflow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var areaName: String? = null,
    @ManyToMany
    var transitions: Set<Transition> = emptySet()
) : GenericEntity()