package com.aris.booklibraries.demoBookLibraries.converters

class IntValueConverter: ValueConverter<Int> {
    override val targetType: Class<out Int> = Int::class.java

    override fun convert(value: Boolean): Int? {
        return value.toString().toInt()
    }

    override fun convert(value: Short): Int? {
        return value.toString().toInt()
    }

    override fun convert(value: Char): Int? {
        return value.toString().toInt()
    }

    override fun convert(value: Int): Int? {
        return value
    }

    override fun convert(value: Long): Int? {
        return value.toString().toInt()
    }

    override fun convert(value: Float): Int? {
        return value.toString().toInt()
    }

    override fun convert(value: Double): Int? {
        return value.toString().toInt()
    }

    override fun convert(value: String): Int? {
        return value.toInt()
    }
}