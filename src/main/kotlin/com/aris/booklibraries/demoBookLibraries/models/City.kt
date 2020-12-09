package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class City(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "city_id")
        var cityId: Long?,
        var name: String?
)