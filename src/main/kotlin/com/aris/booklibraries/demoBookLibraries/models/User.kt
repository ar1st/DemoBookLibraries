package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Long?,
                var email: String,
                var firstName: String?,
                var lastName: String?
                )
