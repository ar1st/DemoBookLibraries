package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var title: String?,
        @ManyToOne
        @JoinColumn(name="id", nullable=false,insertable = false,updatable = false)
        var author: Author?

        )