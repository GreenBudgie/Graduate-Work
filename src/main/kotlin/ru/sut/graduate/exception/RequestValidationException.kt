package ru.sut.graduate.exception

/**
 * To be thrown in controllers
 */
class RequestValidationException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception()