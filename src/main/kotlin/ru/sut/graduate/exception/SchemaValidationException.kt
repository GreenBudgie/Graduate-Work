package ru.sut.graduate.exception

class SchemaValidationException(
    private val required: Set<String>,
    private val provided: Set<String>,
    override val cause: Throwable? = null
) : Exception() {

    override val message: String
        get() {
            var msg = "Схема не прошла проверку"
            getExtraParametersInfo()?.let { msg += "; $it" }
            getMissingParametersInfo()?.let { msg += "; $it" }
            return msg
        }

    private fun getExtraParametersInfo(): String? {
        val extra = provided - required
        if(extra.isEmpty()) return null
        val info = extra.joinToString(", ")
        return "Лишние параметры: $info"
    }

    private fun getMissingParametersInfo(): String? {
        val missing = required - provided
        if(missing.isEmpty()) return null
        val info = missing.joinToString(",")
        return "Отсутствующие параметры: $info"
    }

}