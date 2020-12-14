package com.aris.booklibraries.demoBookLibraries.converters


class StringValueConverter: ValueConverter<String>
{
    override val targetType: Class<out String> = String::class.java

    override fun convert(value: Boolean): String? {
        return value.toString()
    }

    override fun convert(value: Short): String? {
        return value.toString()
    }

    override fun convert(value: Char): String? {
        return value.toString()
    }

    override fun convert(value: Int): String? {
        return value.toString()
    }

    override fun convert(value: Long): String? {
        return value.toString()
    }

    override fun convert(value: Float): String? {
        return value.toString()
    }

    override fun convert(value: Double): String? {
        return value.toString()
    }

    override fun convert(value: String): String? {
        return value
    }

}