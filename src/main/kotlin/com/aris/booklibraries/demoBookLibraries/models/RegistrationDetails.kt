package com.aris.booklibraries.demoBookLibraries.models

data class RegistrationDetails(
    var username: String?,
    var password: String?,
    var firstName: String?,
    var lastName: String?,
    var library: Library?
)
