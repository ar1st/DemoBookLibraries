package com.aris.booklibraries.demoBookLibraries.models

import javax.persistence.*

@Deprecated("should be replaced")
@Entity
data class UserOld(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var userId: Long?,
                   var email: String?,
                   var password: String?,
                   var firstName: String?,
                   var lastName: String?,

//                @OneToOne(targetEntity = Address::class,cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
//                var address: Address?
)
