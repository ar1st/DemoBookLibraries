package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Librarian
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.LibrarianService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LibrarianExecutor {
    @Autowired
    lateinit var librarianService: LibrarianService
    fun logIn(email: String,password: String): ApiResponse<Librarian, String> {
        if ( email.isEmpty() || password.isEmpty()) {
            return ApiResponse(data = null, message = "Error")
        }

        val librarianToLogin = librarianService.logIn(email,password)
            ?: return ApiResponse(data = null, message = "Wrong username or pass")

        return ApiResponse(data = librarianToLogin, message = "OK")
    }
}