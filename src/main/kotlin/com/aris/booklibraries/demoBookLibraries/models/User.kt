package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Entity
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var userId: Long?,
                var email: String?,
                var firstName: String?,
                var lastName: String?,

//                @OneToOne(targetEntity = Address::class,cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
//                var address: Address?
)
