package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
class HasBook (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="has_book_id")
    var hasBookId: Long?,

    @ManyToOne
  //  @MapsId("libraryId")
    @JoinColumn(name = "library_id")
    var library: Library,

    @ManyToOne
   // @MapsId("bookId")
    @JoinColumn(name = "book_id")
    var book: Book,

    var quantity: Long
)