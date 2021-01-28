package com.aris.booklibraries.demoBookLibraries.models

data class RegistrationDetails(
    var email: String?,
    var password: String?,
    var firstName: String?,
    var lastName: String?,
    var library: Library?
)
