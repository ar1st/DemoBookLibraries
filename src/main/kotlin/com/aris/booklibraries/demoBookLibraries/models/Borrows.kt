package com.aris.booklibraries.demoBookLibraries.models

import java.time.LocalDate
import javax.persistence.*

@Entity
class Borrows(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="borrows_id")
    var borrowsId: Long?,

    @ManyToOne
    // @MapsId("bookId")
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    //  @MapsId("libraryId")
    @JoinColumn(name = "has_book_id")
    var hasBook: HasBook,

    var borrowingDate: LocalDate?,
    var returnedDate: LocalDate?
)