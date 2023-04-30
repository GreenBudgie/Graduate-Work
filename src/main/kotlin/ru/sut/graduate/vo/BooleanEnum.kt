package ru.sut.graduate.vo

enum class BooleanEnum(
    val l10nName: String
) : LocalizableEnum {

    TRUE("Да"),
    FALSE("Нет");

    fun toBoolean() = when(this) {
        TRUE -> true
        FALSE -> false
    }

    override fun getLocalizedName() = l10nName

    companion object {

        fun from(booleanValue: Boolean) = when(booleanValue) {
            true -> TRUE
            false -> FALSE
        }

    }

}