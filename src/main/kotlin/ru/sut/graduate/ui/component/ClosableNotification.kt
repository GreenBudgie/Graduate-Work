package ru.sut.graduate.ui.component

import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.theme.lumo.LumoIcon

class ClosableNotification private constructor(
    text: String,
    duration: Int
) : Notification() {

    init {
        this.duration = duration
        position = Position.BOTTOM_CENTER

        val closeButton = Button(LumoIcon.CROSS.create())
        closeButton.themeName = ButtonVariant.LUMO_TERTIARY_INLINE.variantName
        closeButton.addClickListener { isOpened = false }

        val layout = HorizontalLayout(Div(Text(text)), closeButton)
        layout.alignItems = FlexComponent.Alignment.CENTER

        add(layout)
        isOpened = true
    }

    companion object {

        private const val durationPerSymbol = 25

        fun error(text: String = "Ошибка") {
            val baseDuration = 3250
            val additionalDuration = text.length * durationPerSymbol
            error(text, baseDuration + additionalDuration)
        }

        fun error(text: String = "Ошибка", duration: Int) {
            ClosableNotification(text, duration).addThemeVariants(NotificationVariant.LUMO_ERROR)
        }

        fun success(text: String = "Успешно") {
            val baseDuration = 2250
            val additionalDuration = text.length * durationPerSymbol
            success(text, baseDuration + additionalDuration)
        }

        fun success(text: String = "Успешно", duration: Int) {
            ClosableNotification(text, duration).addThemeVariants(NotificationVariant.LUMO_SUCCESS)
        }

    }

}