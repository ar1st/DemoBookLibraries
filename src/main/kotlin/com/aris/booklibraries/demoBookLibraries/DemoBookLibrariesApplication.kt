package com.aris.booklibraries.demoBookLibraries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate

@SpringBootApplication
class DemoBookLibrariesApplication

fun main(args: Array<String>) {
	runApplication<DemoBookLibrariesApplication>(*args)
}
