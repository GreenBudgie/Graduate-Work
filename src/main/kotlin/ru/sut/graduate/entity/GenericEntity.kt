package ru.sut.graduate.entity

import java.io.Serializable
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class GenericEntity(
    @Transient
    open var id: Long? = null
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenericEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}