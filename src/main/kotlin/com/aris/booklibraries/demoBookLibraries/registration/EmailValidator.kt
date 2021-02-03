package com.aris.booklibraries.demoBookLibraries.registration

import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class EmailValidator: Predicate<String> {
    private val EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
    override fun test(email: String): Boolean {
        //todo implement this.
        return email.matches(EMAIL_PATTERN.toRegex());
    }
}