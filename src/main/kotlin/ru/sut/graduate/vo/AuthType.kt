package ru.sut.graduate.vo

enum class AuthType(
    val l10nName: String
) : LocalizableEnum {

    TOKEN("Token"),
    CARD("Card"),
    RFID("RFID");

    override fun getLocalizedName() = l10nName

}