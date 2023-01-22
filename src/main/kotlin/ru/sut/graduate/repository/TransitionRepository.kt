package ru.sut.graduate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sut.graduate.entity.Transition

interface TransitionRepository : JpaRepository<Transition, Long>