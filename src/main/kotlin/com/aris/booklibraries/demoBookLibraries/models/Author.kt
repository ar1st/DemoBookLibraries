package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class Author (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var email: String,
        var firstName: String?,
        var lastName: String?,
        @OneToMany(mappedBy="author")
        var books: MutableSet<Book>?
)