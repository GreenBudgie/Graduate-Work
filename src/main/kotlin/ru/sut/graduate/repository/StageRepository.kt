package ru.sut.graduate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sut.graduate.domain.Stage

interface StageRepository : JpaRepository<Stage, Long>