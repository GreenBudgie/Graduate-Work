package ru.sut.graduate.exception

/**
 * To be thrown on failing to perform entity save/delete operations in services
 */
class ServiceException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception()