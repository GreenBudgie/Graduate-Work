package ru.sut.graduate.vo

enum class Browser(
    val l10nName: String
) : LocalizableEnum {

    CHROME("Google Chrome"),
    MOZILLA("Mozilla Firefox"),
    EXPLORER("Internet Explorer"),
    EDGE("Microsoft Edge"),
    YANDEX("Yandex Browser"),
    OPERA("Opera");

    override fun getLocalizedName() = l10nName

}