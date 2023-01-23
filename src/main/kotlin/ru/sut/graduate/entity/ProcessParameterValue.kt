package ru.sut.graduate.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@IdClass(ProcessParameterValueKey::class)
class ProcessParameterValue(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    var process: Process? = null,
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    var processParameter: ProcessParameter? = null,
    var value: String? = null
) : GenericEntity()

data class ProcessParameterValueKey(
    val process: Long? = null,
    val processParameter: Long? = null,
) : Serializable