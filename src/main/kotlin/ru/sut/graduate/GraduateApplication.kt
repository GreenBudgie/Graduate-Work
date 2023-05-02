package ru.sut.graduate

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import com.vaadin.flow.theme.material.Material
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Theme(themeClass = Lumo::class, variant = Lumo.DARK)
class GraduateApplication : AppShellConfigurator

fun main(args: Array<String>) {
	runApplication<GraduateApplication>(*args)
}
