package com.aris.booklibraries.demoBookLibraries.converters

interface ValueConverter<V> {
    val targetType: Class<out V>

    fun convert(value: Boolean): V?
    fun convert(value: Short): V?
    fun convert(value: Char): V?
    fun convert(value: Int): V?
    fun convert(value: Long): V?
    fun convert(value: Float): V?
    fun convert(value: Double): V?
    fun convert(value: String): V?
}