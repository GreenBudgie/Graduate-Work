package ru.sut.graduate.entity

import javax.persistence.Entity

@Entity
class Stage(
    id: Long? = null,
    var name: String? = null,
    var description: String? = null,
) : GenericEntity(id)