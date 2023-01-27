package ru.sut.graduate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sut.graduate.entity.Schema

interface SchemaRepository : JpaRepository<Schema, Long>