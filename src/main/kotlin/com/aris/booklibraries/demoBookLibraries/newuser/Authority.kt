package com.aris.booklibraries.demoBookLibraries.newuser

import javax.persistence.*

@Entity
data class Authority (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var authorityId: Long,
    @OneToOne
//    @PrimaryKeyJoinColumn
    @JoinColumn(name = "email")
    var newUser : NewUser?,
    var authority: String?
        )