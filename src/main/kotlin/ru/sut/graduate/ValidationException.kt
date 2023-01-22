package ru.sut.graduate

import ru.sut.graduate.ui.component.ClosableNotification

class ValidationException(
    override val message: String
) : Exception() {

    fun showErrorNotification() = ClosableNotification.error(message)

}