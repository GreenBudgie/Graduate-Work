package ru.sut.graduate.exception

import ru.sut.graduate.service.GenericService

/**
 * To be thrown in [GenericService.validate]
 */
class EntityValidationException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception()