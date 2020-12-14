package com.aris.booklibraries.demoBookLibraries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoBookLibrariesApplication

fun main(args: Array<String>) {
	runApplication<DemoBookLibrariesApplication>(*args)
//	val classes = listOf(
//		Boolean::class.java,
//		java.lang.Boolean::class.java,
//		Short::class.java,
//		java.lang.Short::class.java,
//		Char::class.java,
//		java.lang.Character::class.java,
//		Int::class.java,
//		java.lang.Integer::class.java,
//		Long::class.java,
//		java.lang.Long::class.java,
//		Float::class.java,
//		java.lang.Float::class.java,
//		Double::class.java,
//		java.lang.Double::class.java,
//		String::class.java,
//		java.lang.String::class.java,
//	)


//	for (cls in classes) {
//		println("$cls type is ${cls.typeName}")
//
//
//		println("${cls.canonicalName} type is ${cls.typeName}")
//		println("---------------------------------------------------------------------------")
//	}
}
