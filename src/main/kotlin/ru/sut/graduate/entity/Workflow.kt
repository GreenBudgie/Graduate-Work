package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Workflow(
    id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var areaName: String? = null,
    @ManyToMany
    @JoinTable
    var transitions: List<Transition>? = emptyList()
) : GenericEntity(id)