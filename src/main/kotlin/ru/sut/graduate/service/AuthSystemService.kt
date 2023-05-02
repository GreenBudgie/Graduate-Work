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
        val matcher = ExampleMatcher.matching().withIgnoreNullValues()
        val exampleQuery = Example.of(example, matcher)
        val result = repository.findAll(exampleQuery)
        return result
            .filter { it.supportedOS.containsAll(example.supportedOS) }
            .filter { it.supportedBrowsers.containsAll(example.supportedBrowsers) }
    }

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