package ru.sut.graduate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sut.graduate.entity.ProcessParameterValue

interface ProcessParameterValueRepository : JpaRepository<ProcessParameterValue, Long>