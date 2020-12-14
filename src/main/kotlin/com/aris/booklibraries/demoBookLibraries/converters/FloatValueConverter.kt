package com.aris.booklibraries.demoBookLibraries.converters

class FloatValueConverter: ValueConverter<Float> {
    override val targetType: Class<out Float> = Float::class.java
    override fun convert(value: Boolean): Float? {
        return value.toString().toFloat()
    }

    override fun convert(value: Short): Float? {
        return value.toString().toFloat()
    }

    override fun convert(value: Char): Float? {
        return value.toString().toFloat()
    }

    override fun convert(value: Int): Float? {
        return value.toString().toFloat()
    }

    override fun convert(value: Long): Float? {
        return value.toString().toFloat()
    }

    override fun convert(value: Float): Float? {
        return value
    }

    override fun convert(value: Double): Float? {
        return value.toString().toFloat()
    }

    override fun convert(value: String): Float? {
        return value.toFloat()
    }
}