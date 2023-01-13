package ru.sut.graduate.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Process(
    @Id
    @GeneratedValue
    val id: Long,
    @ManyToOne
    val stage: Stage,
) {

    override fun toString(): String {
        return "Process(id=$id, stage=${stage})"
    }

}