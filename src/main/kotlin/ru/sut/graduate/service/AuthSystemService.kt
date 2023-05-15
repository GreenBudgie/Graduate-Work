package ru.sut.graduate.service

import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import ru.sut.graduate.entity.AuthSystem
import ru.sut.graduate.exception.EntityValidationException
import ru.sut.graduate.repository.AuthSystemRepository


@Service
class AuthSystemService : GenericService<AuthSystem, AuthSystemRepository>() {

    fun findByExampleSoftware(example: AuthSystem): List<AuthSystem> {
        return repository.findAll()
            .asSequence()
            .filter { it.segments greaterThan example.segments }
            .filter { it.hosts greaterThan example.hosts }
            .filter { it.supportedOS containsOne example.supportedOS }
            .filter { example.authType == null || it.authType == example.authType }
            .filter { it.trustFactor greaterThan example.trustFactor }
            .filter { it.keyLength greaterThan example.keyLength }
            .filter { it.supportedBrowsers containsOne example.supportedBrowsers }
            .filter { example.supportsMobile == null || it.supportsMobile == example.supportsMobile }
            .filter { example.supportsDocker == null || it.supportsDocker == example.supportsDocker }
            .filter { it.price lessThan example.price }
            .toList()
    }

    private infix fun Int?.greaterThan(other: Int?) = (this ?: 0) >= (other ?: 0)
    private infix fun Int?.lessThan(other: Int?) = (this ?: 0) <= (other ?: 0)
    private infix fun <T> Set<T>.containsOne(other: Set<T>) = other.isEmpty() || other.any { this.contains(it) }

    override fun validate(entity: AuthSystem) = with(entity) {
        validateFieldUniqueness(entity, AuthSystem::name, "ПО с таким названием уже существует")
        segments.notNegative("Количество сегментов не может быть отрицательным")
        hosts.notNegative("Количество хостов не может быть отрицательным")
        keyLength.notNegative("Длина ключа не может быть отрицательной")
        price.notNegative("Цена не может быть отрицательной")
        if((trustFactor ?: 0) !in (0..3)) {
            throw EntityValidationException("Уровень доверия определяется значением от 0 до 3х")
        }
    }

    private fun Int?.notNegative(message: String) {
        this ?: return
        if(this < 0) throw EntityValidationException(message)
    }

}