package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="book_id")
        var bookId: Long?,
        var title: String?,
        @ManyToOne(targetEntity = Author::class, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name="author_id")
        var author: Author?
        )


