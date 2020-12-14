package com.aris.booklibraries.demoBookLibraries.models

import com.aris.booklibraries.demoBookLibraries.converters.Converter.convert
import java.util.*
import javax.persistence.*

@Entity
data class Author (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "author_id")
        var authorId: Long?,
        var email: String,
        var firstName: String?,
        var lastName: String?
      //  @OneToMany(mappedBy="author_id")
      //  var books: MutableList<Book>?
) {
        fun patch(patch: HashMap<String,Any>) {
                val klass: Class<out Author> = this::class.java
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
                        val convertedPatchValue = convert(patchValue, fieldType) ?: continue

                        method.invoke(this, convertedPatchValue)
                }
        }
}