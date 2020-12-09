package com.aris.booklibraries.demoBookLibraries.models

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
)