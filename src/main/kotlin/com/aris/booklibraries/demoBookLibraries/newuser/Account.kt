package com.aris.booklibraries.demoBookLibraries.newuser

import javax.persistence.*

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var accountId: Long,
    var username: String?,
    var password: String?,
    var enabled: Int
    )