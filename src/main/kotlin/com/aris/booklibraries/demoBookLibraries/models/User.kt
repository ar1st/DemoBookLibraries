package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long?,
    var firstName:String?,
    var lastName:String?,
    @OneToOne
    @JoinColumn(name = "account_id")
    var account : Account?,
)
