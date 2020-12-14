package com.aris.booklibraries.demoBookLibraries.converters

class LongValueConverter: ValueConverter<Long> {
    override val targetType: Class<out Long> = Long::class.java

    override fun convert(value: Boolean): Long? {
        return value.toString().toLong()
    }

    override fun convert(value: Short): Long? {
        return value.toString().toLong()
    }

    override fun convert(value: Char): Long? {
        return value.toString().toLong()
    }

    override fun convert(value: Int): Long? {
        return value.toString().toLong()
    }

    override fun convert(value: Long): Long? {
        return value
    }

    override fun convert(value: Float): Long? {
        return value.toString().toLong()
    }

    override fun convert(value: Double): Long? {
        return value.toString().toLong()
    }

    override fun convert(value: String): Long? {
        return value.toLong()
    }
}