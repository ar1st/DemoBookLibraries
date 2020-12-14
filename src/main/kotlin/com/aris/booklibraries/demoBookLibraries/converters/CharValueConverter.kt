package com.aris.booklibraries.demoBookLibraries.converters

class CharValueConverter: ValueConverter<Char> {
    override val targetType: Class<out Char> = Char::class.java

    override fun convert(value: Boolean): Char? {
         return if ( value ) 'T' else 'F'
    }

    override fun convert(value: Short): Char? {
        return value.toChar()
    }

    override fun convert(value: Char): Char? {
        return value
    }

    override fun convert(value: Int): Char? {
        return value.toChar()
    }

    override fun convert(value: Long): Char? {
        return value.toChar()
    }

    override fun convert(value: Float): Char? {
        return value.toChar()
    }

    override fun convert(value: Double): Char? {
        return value.toChar()
    }

    override fun convert(value: String): Char? {
        return value.firstOrNull()
    }
}