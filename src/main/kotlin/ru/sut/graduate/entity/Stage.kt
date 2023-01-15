package ru.sut.graduate.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Stage(
    id: Long? = null,
    var name: String? = null,
    var description: String? = null,
) : GenericEntity(id)