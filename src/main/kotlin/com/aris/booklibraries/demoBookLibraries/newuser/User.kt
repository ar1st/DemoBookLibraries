package com.aris.booklibraries.demoBookLibraries.newuser

import javax.persistence.*

@Entity
data class UserNew(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long?,
    var firstName:String?,
    var lastName:String?,
    @OneToOne
    @JoinColumn(name = "account_id")
    var account : Account?,
)
