package ru.sut.graduate.ui.component

import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.theme.lumo.LumoIcon

class ErrorNotification(
    text: String
) : Notification() {

    init {
        addThemeVariants(NotificationVariant.LUMO_ERROR)
        duration = 8000
        position = Position.BOTTOM_CENTER

        val closeButton = Button(LumoIcon.CROSS.create())
        closeButton.themeName = ButtonVariant.LUMO_TERTIARY_INLINE.variantName
        closeButton.addClickListener { isOpened = false }

        val layout = HorizontalLayout(Div(Text(text)), closeButton)
        layout.alignItems = FlexComponent.Alignment.CENTER

        add(layout)
        isOpened = true
    }

}