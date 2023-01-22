package ru.sut.graduate.service

import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.sut.graduate.ValidationException
import ru.sut.graduate.entity.GenericEntity
import ru.sut.graduate.ui.component.ClosableNotification
import kotlin.reflect.KProperty1

@Service
abstract class GenericService<E : GenericEntity, R : JpaRepository<E, Long>> {

    @set:Autowired
    protected lateinit var repository: R

    /**
     * Validates the specified entity and saves it to database,
     * either updating the existing entity or creating a new one.
     *
     * @throws ValidationException if [validate] method restricted this operation
     * or any other exception is thrown, usually [ConstraintViolationException]
     * @return An instance of the saved entity
     */
    @Throws(ValidationException::class)
    final fun save(entity: E): E {
        validate(entity)
        try {
            return repository.save(entity)
        } catch(exception: Exception) {
            if (exception is DataIntegrityViolationException || exception is ConstraintViolationException) {
                throw ValidationException("Указано неуникальное значение для одного из полей")
            }
            throw ValidationException("Непредвиденная ошибка при сохранении")
        }
    }

    /**
     * Deletes the entity with the given ID from the database.
     *
     * @throws ValidationException if there is no entity with such id or this entity cannot be deleted
     */
    @Throws(ValidationException::class)
    final fun delete(id: Long) {
        if(!repository.existsById(id)) {
            throw ValidationException("Сущности с ID $id не существует")
        }
        try {
            repository.deleteById(id)
        } catch(exception: Exception) {
            if(exception is DataIntegrityViolationException
                || exception is ConstraintViolationException) {
                throw ValidationException(
                    "Невозможно удалить сущность, так как одна из других сущностей ссылается на нее"
                )
            }
            throw ValidationException("Непредвиденная ошибка при удалении")
        }
    }

    /**
     * Deletes the given entity from the database
     *
     * @throws ValidationException if there is no entity with such id, id is not specified
     * or this entity cannot be deleted
     */
    @Throws(ValidationException::class)
    final fun delete(entity: E) {
        val id = entity.id ?: throw ValidationException("Невозможно удалить сущность без ID")
        delete(id)
    }

    final fun findById(id: Long): E? = repository.findById(id).orElse(null)

    final fun findAll(): List<E> = repository.findAll()

    final fun findAllButThis(entity: E): List<E> = findAll().filter { it.id != entity.id }

    /**
     * Performs the delete operation on the given entity and shows notification if it's impossible.
     *
     * @return whether the operation was successful
     */
    final fun deleteOnUI(id: Long) = invokeWithNotification("Успешно удалено") { delete(id) }

    /**
     * Performs the delete operation on the given entity by its id and shows notification if it's impossible.
     *
     * @return whether the operation was successful
     */
    final fun deleteOnUI(entity: E) = invokeWithNotification("Успешно удалено") { delete(entity) }

    /**
     * Saves the given entity with validation if it's possible and returns the saved entity.
     * If not, shows the notification on UI and returns null.
     */
    final fun saveOnUI(entity: E) = invokeWithNotification("Успешно сохранено") { save(entity)}

    /**
     * Validates the entity before save operation.
     * @throws ValidationException if the entity that is about to save is
     */
    @Throws(ValidationException::class)
    fun validate(entity: E) {}

    @Throws(ValidationException::class)
    protected final fun validateFieldUniqueness(entity: E,
                                                field: KProperty1<E, Any?>,
                                                messageOnFail: String) {
        val fieldValue = field.get(entity) ?: return
        val entities = findAllButThis(entity)
        val hasSameFieldValue = entities.any {
            fieldValue == field.get(it)
        }
        if(hasSameFieldValue) throw ValidationException(messageOnFail)
    }

    private inline fun invokeWithNotification(message: String = "Успешно",
                                              crossinline operation: () -> Unit): Boolean {
        try {
            operation()
            ClosableNotification.success(message)
            return true
        } catch(validationException: ValidationException) {
            validationException.showErrorNotification()
        }
        return false
    }

}