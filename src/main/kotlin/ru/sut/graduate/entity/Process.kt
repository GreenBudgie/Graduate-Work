package ru.sut.graduate.entity

import javax.persistence.*

@Entity
class Process(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var stage: Stage? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var workflow: Workflow? = null,
    @OneToMany(mappedBy = "process", fetch = FetchType.LAZY)
    var parameters: Set<ProcessParameterValue>? = null
) : GenericEntity()