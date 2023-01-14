package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Process(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    val stage: Stage? = null,
)