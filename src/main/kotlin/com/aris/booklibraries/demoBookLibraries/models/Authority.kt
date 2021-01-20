package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class Authority (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var authorityId: Long?,
    @OneToOne
    @JoinColumn(name = "account_id")
    var account : Account?,
    var authority: String?
        )