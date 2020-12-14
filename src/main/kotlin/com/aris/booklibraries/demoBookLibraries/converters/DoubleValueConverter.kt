package com.aris.booklibraries.demoBookLibraries.converters

class DoubleValueConverter: ValueConverter<Double> {
    override val targetType: Class<out Double> = Double::class.java

    override fun convert(value: Boolean): Double? {
        return value.toString().toDouble()
    }

    override fun convert(value: Short): Double? {
        return value.toString().toDouble()
    }

    override fun convert(value: Char): Double? {
        return value.toString().toDouble()
    }

    override fun convert(value: Int): Double? {
        return value.toString().toDouble()
    }

    override fun convert(value: Long): Double? {
        return value.toString().toDouble()
    }

    override fun convert(value: Float): Double? {
        return value.toString().toDouble()
    }

    override fun convert(value: Double): Double? {
        return value
    }

    override fun convert(value: String): Double? {
        return value.toDouble()
    }
}