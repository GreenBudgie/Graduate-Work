package ru.sut.graduate.service

import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.ui.component.ClosableNotification
import kotlin.reflect.KProperty1

@Service
abstract class GenericService<E : GenericEntity, R : JpaRepository<E, Long>> {

    @set:Autowired
    protected lateinit var repository: R

    /**
     * Saves the given entity.
     * If the entity is about to be saved on the UI, use [saveOnUI].
     */
    final fun save(entity: E) = repository.save(entity)

    final fun delete(id: Long) = repository.deleteById(id)

    final fun delete(entity: E) = repository.delete(entity)

    final fun findById(id: Long): E? = repository.findById(id).orElse(null)

    final fun findAll(): List<E> = repository.findAll()

    /**
     * Saves the given entity with validation if it's possible and returns the saved entity.
     * If not, shows the notification on UI and returns null.
     */
    final fun saveOnUI(entity: E): E? {
        if(!validate(entity)) {
            return null
        }
        try {
            val savedEntity = save(entity)
            ClosableNotification.success("Успешно сохранено")
            return savedEntity
        } catch(exception: Exception) {
            if(exception is DataIntegrityViolationException
                || exception is ConstraintViolationException) {
                ClosableNotification.error("Указано неуникальное значение для одного из полей")
            } else {
                ClosableNotification.error("Непредвиденная ошибка при сохранении")
            }
        }
        return null
    }

    /**
     * Validates the entity before save operation.
     * Only invoked from [saveOnUI], so it must not throw any exceptions, only show UI notifications.
     */
    fun validate(entity: E) = true

    protected final fun isFieldNotUnique(entity: E, field: KProperty1<E, Any?>): Boolean {
        val fieldValue = field.get(entity) ?: return false
        val entities = findAll()
        val hasSameFieldValue = entities.any {
            fieldValue == field.get(it)
        }
        return hasSameFieldValue
    }

}