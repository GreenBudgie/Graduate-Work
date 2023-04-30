package ru.sut.graduate.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Software(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    var name: String? = null,
    var price: Int? = null,
) : GenericEntity()