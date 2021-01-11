package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class Librarian(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var librarianId: Long?,
    var email: String?,
    var password: String?,
    var firstName: String?,
    var lastName: String?,
    @ManyToOne(targetEntity = Library::class, cascade = [CascadeType.DETACH], fetch = FetchType.EAGER)
    @JoinColumn(name="library_id")
    var library: Library?
)