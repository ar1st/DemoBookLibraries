package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
//@Table(name="library")
data class Library (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "library_id")
        var libraryId: Long?,
        var name: String?,
        @ManyToOne(targetEntity = City::class, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name="city_id")
        var city: City?,
        @ManyToMany(cascade = [CascadeType.DETACH])
        @JoinTable(
                name = "has_books",
                joinColumns = [JoinColumn(name = "library_id")],
                inverseJoinColumns = [JoinColumn(name = "book_id")]
        )
        var books: MutableList<Book>
        )

