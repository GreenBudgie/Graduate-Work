package ru.sut.graduate.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Stage(
    @Id
    @GeneratedValue
    val id: Long,
    val name: String,
) {

    override fun toString(): String {
        return "Stage(id=$id, name='$name')"
    }

}