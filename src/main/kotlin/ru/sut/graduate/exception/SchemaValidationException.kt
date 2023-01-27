package ru.sut.graduate.exception

class SchemaValidationException(
    private val required: Set<String>,
    private val provided: Set<String>,
    override val cause: Throwable? = null
) : Exception() {

    override val message: String
        get() = "Схема не прошла проверку; ${getExtraParametersInfo()}; ${getMissingParametersInfo()}"

    private fun getExtraParametersInfo(): String {
        val extra = provided - required
        val info = extra.joinToString(",")
        return "Лишние параметры: $info"
    }

    private fun getMissingParametersInfo(): String {
        val missing = required - provided
        val info = missing.joinToString(",")
        return "Отсутствующие параметры: $info"
    }

}