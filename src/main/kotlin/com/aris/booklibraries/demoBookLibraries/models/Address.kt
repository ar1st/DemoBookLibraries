package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class Address (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
       // @Column(name="address_id")
        var addressId: Long?,
        var roadName: String?,
        var roadNumber: String?,
        var tk: Long?,
        var country: String?,
)