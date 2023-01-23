package ru.sut.graduate.service

import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.exception.EntityValidationException
import ru.sut.graduate.exception.ServiceException
import ru.sut.graduate.ui.component.ClosableNotification
import kotlin.reflect.KProperty1

@Service
abstract class GenericService<E : GenericEntity, R : JpaRepository<E, Long>> {

    @set:Autowired
    protected lateinit var repository: R

    /**
     * Validates the specified entity and saves it to the database,
     * either updating the existing entity or creating a new one.
     *
     * @throws EntityValidationException if [validate] method restricted this operation
     * @throws ServiceException if any other problem is encountered during this operation
     * @return An instance of the saved entity
     */
    @Throws(EntityValidationException::class, ServiceException::class)
    final fun save(entity: E): E {
        validate(entity)
        try {
            return repository.save(entity)
        } catch(exception: Exception) {
            if (exception is DataIntegrityViolationException || exception is ConstraintViolationException) {
                throw ServiceException("Указано неуникальное значение для одного из полей", exception)
            }
            throw ServiceException("Непредвиденная ошибка при сохранении", exception)
        }
    }

    /**
     * Deletes the entity with the given ID from the database.
     *
     * @throws ServiceException if there is no entity with such id or this entity cannot be deleted
     */
    @Throws(ServiceException::class)
    final fun delete(id: Long) {
        if(!repository.existsById(id)) {
            throw ServiceException("Сущности с ID $id не существует")
        }
        try {
            repository.deleteById(id)
        } catch(exception: Exception) {
            if(exception is DataIntegrityViolationException
                || exception is ConstraintViolationException) {
                throw ServiceException(
                    "Невозможно удалить сущность, так как одна из других сущностей ссылается на нее",
                    exception
                )
            }
            throw ServiceException("Непредвиденная ошибка при удалении", exception)
        }
    }

    /**
     * Deletes the given entity from the database
     *
     * @throws ServiceException if there is no entity with such id, id is not specified
     * or this entity cannot be deleted
     */
    @Throws(EntityValidationException::class)
    final fun delete(entity: E) {
        val id = entity.id ?: throw ServiceException("Невозможно удалить сущность без ID")
        delete(id)
    }

    final fun findById(id: Long): E? = repository.findById(id).orElse(null)

    final fun findAll(): List<E> = repository.findAll()

    final fun findAllButThis(entity: E): List<E> = findAll().filter { it.id != entity.id }

    final fun deleteOnUI(id: Long) = invokeOnUI("Успешно удалено") { delete(id) }

    final fun deleteOnUI(entity: E) = invokeOnUI("Успешно удалено") { delete(entity) }

    final fun saveOnUI(entity: E) = invokeOnUI("Успешно сохранено") { save(entity) }

    /**
     * Validates the entity before save operation.
     * @throws EntityValidationException if validation fails
     */
    @Throws(EntityValidationException::class)
    fun validate(entity: E) {}

    @Throws(EntityValidationException::class)
    protected final fun validateFieldUniqueness(
        entity: E,
        field: KProperty1<E, Any?>,
        messageOnFail: String
    ) {
        val fieldValue = field.get(entity) ?: return
        val entities = findAllButThis(entity)
        val hasSameFieldValue = entities.any {
            fieldValue == field.get(it)
        }
        if(hasSameFieldValue) throw EntityValidationException(messageOnFail)
    }

    private inline fun <T> invokeOnUI(successMessage: String = "Успешно",
                                      crossinline operation: () -> T): T {
        try {
            val result = operation()
            ClosableNotification.success(successMessage)
            return result
        } catch(exception: Exception) {
            val error = exception.message ?: "Непредвиденная ошибка"
            ClosableNotification.error(error)
            throw exception
        }
    }

}