package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Transition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var fromStage: Stage? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var toStage: Stage? = null,
)