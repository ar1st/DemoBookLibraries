package com.aris.booklibraries.demoBookLibraries.models

import org.hibernate.annotations.Cascade
import javax.persistence.*

@Entity
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="book_id")
        var bookId: Long?,
        var title: String?,
        @ManyToOne(targetEntity = Author::class, cascade = [CascadeType.DETACH], fetch = FetchType.EAGER)
        @JoinColumn(name="author_id")
        //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
        var author: Author?
        )


