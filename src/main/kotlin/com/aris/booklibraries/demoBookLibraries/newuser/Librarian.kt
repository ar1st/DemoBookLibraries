package com.aris.booklibraries.demoBookLibraries.newuser

import com.aris.booklibraries.demoBookLibraries.models.Library
import javax.persistence.*

@Entity
data class LibrarianNew(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var librarianId: Long?,
    var firstName:String?,
    var lastName:String?,
    @ManyToOne(targetEntity = Library::class, cascade = [CascadeType.DETACH], fetch = FetchType.EAGER)
    @JoinColumn(name="library_id")
    var library: Library?,
    @OneToOne
    @JoinColumn(name = "account_id")
    var account : Account?,
)
