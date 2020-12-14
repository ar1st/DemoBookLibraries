package com.aris.booklibraries.demoBookLibraries.converters

import kotlin.Exception

object Converter {
    var converters =  mapOf<Class<*>, ValueConverter<*>?>(
        Boolean::class.java to BooleanValueConverter(),
        java.lang.Boolean::class.java to BooleanValueConverter(),
        Short::class.java to ShortValueConverter(),
        java.lang.Short::class.java to ShortValueConverter(),
        Char::class.java to CharValueConverter(),
        java.lang.Character::class.java to CharValueConverter(),
        Int::class.java to IntValueConverter(),
        java.lang.Integer::class.java to IntValueConverter(),
        Long::class.java to LongValueConverter(),
        java.lang.Long::class.java to LongValueConverter(),
        Float::class.java to FloatValueConverter(),
        java.lang.Float::class.java to FloatValueConverter(),
        Double::class.java to DoubleValueConverter(),
        java.lang.Double::class.java to DoubleValueConverter(),
        String::class.java to StringValueConverter(),
        java.lang.String::class.java to StringValueConverter()
    )

    fun <T> convert(value: Any?, clazz: Class<out T>): T? {
        val targetTypeName = clazz.canonicalName

        if (value == null) {
            return null
        }

        try {
            return clazz.cast(value)
        } catch (ex:Exception) {
            // nothing
        }

        if ( converters.keys.any { it.canonicalName == targetTypeName }) {
            val converterKey = converters.keys.firstOrNull { it.canonicalName == targetTypeName }
            val converter: ValueConverter<*> = converters[converterKey] ?: return null
            // convert

            return when(value) {
                is Boolean -> converter.convert(value) as T
                is Int -> converter.convert(value) as T
                is Float -> converter.convert(value) as T
                is Double -> converter.convert(value) as T
                is Short -> converter.convert(value) as T
                is Long -> converter.convert(value) as T
                is String -> converter.convert(value) as T

                else -> null
            }

        }

        return null
    }
}

// Abstract Factory - Factory Method

abstract class MyObject
{
    abstract fun someDependency(): Any?

    fun doSomething() {
        val d = someDependency()
        //////
    }
}


class MyObjectImpl : MyObject()
{
    override fun someDependency(): Any? {
        return 5
    }

}

// Factory

class Seasons(val name: String)
{

    companion object {
        val winter = Seasons("Winter")
        val spring = Seasons("Spring")
        val summer = Seasons("Summer")
        val autumn = Seasons("Autumn")

        fun forName(name: String) : Seasons? {
            return when(name) {
                "winter" -> winter
                else -> null
            }
        }
    }

}

//https://sourcemaking.com/design_patterns