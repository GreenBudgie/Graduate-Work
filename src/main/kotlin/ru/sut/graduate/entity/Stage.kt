package ru.sut.graduate.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.UniqueConstraint

@Entity
class Stage(
    id: Long? = null,
    @Column(unique = true)
    var name: String? = null,
    var description: String? = null,
) : GenericEntity(id)