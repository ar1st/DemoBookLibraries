package com.aris.booklibraries.demoBookLibraries.converters

class BooleanValueConverter: ValueConverter<Boolean> {
    override val targetType: Class<out Boolean> = Boolean::class.java

    override fun convert(value: Boolean): Boolean? {
        return value
    }

    override fun convert(value: Short): Boolean? {
        return value.toString().toBoolean()
    }

    override fun convert(value: Char): Boolean? {
        return value.toString().toBoolean()
    }

    override fun convert(value: Int): Boolean? {
        return value.toString().toBoolean()
    }

    override fun convert(value: Long): Boolean? {
        return value.toString().toBoolean()
    }

    override fun convert(value: Float): Boolean? {
        return value.toString().toBoolean()
    }

    override fun convert(value: Double): Boolean? {
        return value.toString().toBoolean()
    }

    override fun convert(value: String): Boolean? {
        return value.toBoolean()
    }
}