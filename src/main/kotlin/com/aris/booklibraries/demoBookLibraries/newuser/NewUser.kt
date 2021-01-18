package com.aris.booklibraries.demoBookLibraries.newuser

import javax.persistence.*

@Entity
data class NewUser(
    @Id
    var email: String?,
    var userId: Long,
    var password: String?,
    var enabled: Int
    )