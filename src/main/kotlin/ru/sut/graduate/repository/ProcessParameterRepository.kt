package ru.sut.graduate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sut.graduate.entity.ProcessParameter

interface ProcessParameterRepository : JpaRepository<ProcessParameter, Long>