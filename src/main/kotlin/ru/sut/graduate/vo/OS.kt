package ru.sut.graduate.vo

enum class OS(
    val l10nName: String
) : LocalizableEnum {

    WINDOWS("Windows"),
    LINUX("Linux"),
    MAC_OS("macOS"),
    ANDROID("Android"),
    IOS("iOS");

    override fun getLocalizedName() = l10nName

}