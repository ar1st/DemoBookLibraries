package com.aris.booklibraries.demoBookLibraries.models

import com.aris.booklibraries.demoBookLibraries.converters.Converter
import java.util.*
import javax.persistence.*

@Entity
data class Library (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "library_id")
        var libraryId: Long?,
        var name: String?,
        @ManyToOne(targetEntity = City::class, cascade = [CascadeType.DETACH], fetch = FetchType.EAGER)
        @JoinColumn(name="city_id")
        var city: City?,
//        @ManyToMany(cascade = [CascadeType.DETACH])
//        @JoinTable(
//                name = "has_books",
//                joinColumns = [JoinColumn(name = "library_id")],
//                inverseJoinColumns = [JoinColumn(name = "book_id")]
//        )
//        var books: MutableSet<Book>)

//        @OneToMany(mappedBy = "library")
//        var hasBook: MutableSet<HasBook>
        )
{
        fun patch(patch: HashMap<String, Any>) {
                val klass: Class<out Library> = this::class.java
                for (method in klass.methods) {
                        if (!method.name.startsWith("set"))
                                continue

                        if (method.parameterCount != 1)
                                continue

                        if (method.returnType.canonicalName !in listOf("java.lang.Void", "void"))
                                continue

                        val name = method.name.replace("set", "").mapIndexed { i, c ->
                                if (i == 0)
                                        c.toLowerCase()
                                else
                                        c
                        }.joinToString("")

                        val patchValue = patch[name] ?: continue
                        val fieldType = method.parameterTypes[0]
                        val convertedPatchValue = Converter.convert(patchValue, fieldType) ?: continue

                        method.invoke(this, convertedPatchValue)
                }
        }
}

