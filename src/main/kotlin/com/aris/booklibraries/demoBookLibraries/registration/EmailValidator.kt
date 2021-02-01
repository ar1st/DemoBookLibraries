package com.aris.booklibraries.demoBookLibraries.registration

import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class EmailValidator: Predicate<String> {
    override fun test(t: String): Boolean {
        //todo implement this.
        return true;
    }
}