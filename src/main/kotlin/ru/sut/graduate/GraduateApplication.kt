package ru.sut.graduate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraduateApplication

fun main(args: Array<String>) {
	runApplication<GraduateApplication>(*args)
}
