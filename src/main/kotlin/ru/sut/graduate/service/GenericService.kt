package ru.sut.graduate.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.sut.graduate.entity.GenericEntity

@Service
abstract class GenericService<E : GenericEntity, R : JpaRepository<E, Long>> {

    @set:Autowired
    protected lateinit var repository: R

    fun save(entity: E) = repository.save(entity)

    fun delete(id: Long) = repository.deleteById(id)

    fun delete(entity: E) = repository.delete(entity)

    fun findById(id: Long): E? = repository.findById(id).orElse(null)

    fun findAll(): List<E> = repository.findAll()

}