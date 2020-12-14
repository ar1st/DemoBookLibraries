package com.aris.booklibraries.demoBookLibraries.converters

class ShortValueConverter: ValueConverter<Short> {
    override val targetType: Class<out Short> = Short::class.java
    override fun convert(value: Boolean): Short? {
        return value.toString().toShort()
    }

    override fun convert(value: Short): Short? {
        return value
    }

    override fun convert(value: Char): Short? {
        return value.toString().toShort()
    }

    override fun convert(value: Int): Short? {
        return value.toString().toShort()
    }

    override fun convert(value: Long): Short? {
        return value.toString().toShort()
    }

    override fun convert(value: Float): Short? {
        return value.toString().toShort()
    }

    override fun convert(value: Double): Short? {
        return value.toString().toShort()
    }

    override fun convert(value: String): Short? {
        return value.toShort()
    }
}