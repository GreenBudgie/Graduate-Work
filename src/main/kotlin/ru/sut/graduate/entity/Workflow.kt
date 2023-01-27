package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Workflow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    var name: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    var transitions: Set<Transition> = emptySet()
) : GenericEntity()