package ru.sut.graduate.entity

import java.io.Serializable
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class GenericEntity(
    @Transient
    open var id: Long? = null
) : Serializable