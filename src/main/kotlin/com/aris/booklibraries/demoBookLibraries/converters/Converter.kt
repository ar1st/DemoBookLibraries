package com.aris.booklibraries.demoBookLibraries.converters

import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

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

    val primitiveTypes: List<Class<*>>
        get() = converters.keys.toList()

    private fun isPrimitive(klass: Class<*>): Boolean {
        return primitiveTypes.any { it.canonicalName == klass.canonicalName }
    }

    private fun isList(klass: Class<*>?): Boolean {

        if (klass == null)
            return false

        val isAList = klass.canonicalName == List::class.java.canonicalName || klass.canonicalName == MutableList::class.java.canonicalName
        val implementsList = klass.interfaces.any { isList(it) }
        val extendsAList = isList(klass.superclass)

        return isAList || implementsList || extendsAList
    }

    private fun isMap(klass: Class<*>?): Boolean {

        if (klass == null)
            return false

        val isAMap = klass.canonicalName == Map::class.java.canonicalName || klass.canonicalName == MutableMap::class.java.canonicalName
        val implementsMap = klass.interfaces.any { isMap(it) }
        val extendsAMap = isMap(klass.superclass)

        return isAMap || implementsMap || extendsAMap
    }

    fun <T> read(value: Any?, type: Class<T>, subtypes: List<Class<*>> = emptyList()): T? {
        val typeName = type.canonicalName

        if (isPrimitive(type)) {
            try {
                return type.cast(value)
            } catch (ex:Exception) {
                // nothing
            }

            if ( converters.keys.any { it.canonicalName == typeName }) {
                val converterKey = converters.keys.firstOrNull { it.canonicalName == typeName }
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
        } else if (isList(type)) {
            // Is List
            val itemType = subtypes.firstOrNull() ?: return null
            return readList(value as List<*>, itemType) as T
        } else if (isMap(type)) {
            // Is Map
            val keyType = subtypes.getOrNull(0) ?: return null
            val valueType = subtypes.getOrNull(1) ?: return null
            return readMap(value as Map<*, *>, keyType, valueType) as T
        } else {
            // Is Object

            val map = if (value is Map<*,*>)
                value as Map<String, *>
            else
                return null

            val instance = type.newInstance()

            for (key in map.keys) {
                val mapValue = map[key]!!

                val field = type.fields.firstOrNull { it.name == key }

                if (field != null) {
                    val fieldType = field.type

                    if (isList(fieldType)) {
                        val genericType: ParameterizedType = field.genericType as ParameterizedType
                        val genericClass = genericType.actualTypeArguments[0] as Class<*>

                        field.set(instance, read(mapValue, fieldType, listOf(genericClass)))
                    } else if(isMap(fieldType)) {
                        val genericType: ParameterizedType = field.genericType as ParameterizedType
                        val keyGenericClass = genericType.actualTypeArguments[0] as Class<*>
                        val valueGenericClass = genericType.actualTypeArguments[1] as Class<*>

                        field.set(instance, read(mapValue, fieldType, listOf(keyGenericClass, valueGenericClass)))
                    } else {
                        field.set(instance, read(mapValue, fieldType))
                    }

                    continue
                }

                val setter = type.propertySetter(key)

                if (setter != null) {
                    val propertyType = setter.parameterTypes.first()

                    if (isList(propertyType)) {
                        val genericType: ParameterizedType = setter.genericParameterTypes[0] as ParameterizedType
                        val genericClass = genericType.actualTypeArguments[0] as Class<*>

                        setter.invoke(instance, read(mapValue, propertyType, listOf(genericClass)))
                    } else if(isMap(propertyType)) {
                        val genericType: ParameterizedType = setter.genericParameterTypes[0] as ParameterizedType
                        val keyGenericClass = genericType.actualTypeArguments[0] as Class<*>
                        val valueGenericClass = genericType.actualTypeArguments[1] as Class<*>

                        setter.invoke(instance, read(mapValue, propertyType, listOf(keyGenericClass, valueGenericClass)))
                    } else {
                        setter.invoke(instance, read(mapValue, propertyType))
                    }

                    continue
                }

                return null
            }

            return instance

        }

        return null
    }

    fun <T> readList(list: List<*>, itemType: Class<T>): List<T>? {
        val result = mutableListOf<T?>()

        for (item in list) {
            result.add(read(item, itemType))
        }

        return result.mapNotNull { it }
    }

    fun <K,V> readMap(map: Map<*,*>, keyType: Class<K>, valueType: Class<V>): Map<K,V>? {
        val result = mutableMapOf<K,V>()

        for (oldKey in map.keys) {
            val oldValue = map[oldKey]!!

            val key = read(oldKey, keyType) ?: return null
            val value = read(oldValue, valueType) ?: return null

            result[key] = value
        }

        return result
    }

    fun <T> convert(value: Any?, clazz: Class<out T>): T? {
//        val targetTypeName = clazz.canonicalName
//
//        if (value == null) {
//            return null
//        }
//
//        try {
//            return clazz.cast(value)
//        } catch (ex:Exception) {
//            // nothing
//        }
//
//        if ( converters.keys.any { it.canonicalName == targetTypeName }) {
//            val converterKey = converters.keys.firstOrNull { it.canonicalName == targetTypeName }
//            val converter: ValueConverter<*> = converters[converterKey] ?: return null
//            // convert
//
//            return when(value) {
//                is Boolean -> converter.convert(value) as T
//                is Int -> converter.convert(value) as T
//                is Float -> converter.convert(value) as T
//                is Double -> converter.convert(value) as T
//                is Short -> converter.convert(value) as T
//                is Long -> converter.convert(value) as T
//                is String -> converter.convert(value) as T
//
//                else -> null
//            }
//
//        }
//
//        if (value is HashMap<*,*>) {
//            val keys = value.keys
//
//        }
//
//        if (value is List<*>) {
//            //
//        }
//
//        return null
        return read(value, clazz)
    }

    private fun <T> Class<T>.propertySetter(name: String): Method? {
        for (method in methods) {
            if (!method.name.startsWith("set"))
                continue

            if (method.parameterCount != 1)
                continue

            if (method.returnType.canonicalName !in listOf("java.lang.Void", "void"))
                continue

            val propertyName = method.name.replace("set", "").mapIndexed { i, c ->
                if (i == 0)
                    c.toLowerCase()
                else
                    c
            }.joinToString("")

            if (propertyName == name)
                return method

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