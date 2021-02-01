package com.aris.booklibraries.demoBookLibraries.registration.email

interface EmailSender {
    fun send(to: String,email: String)
}