package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.LibrarianOld
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.LibrarianServiceOld
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Deprecated("should be replaced")
@Component
class LibrarianExecutorOld {
    @Autowired
    lateinit var librarianServiceOld: LibrarianServiceOld
    fun logIn(email: String,password: String): ApiResponse<LibrarianOld, String> {
        if ( email.isEmpty() || password.isEmpty()) {
            return ApiResponse(data = null, message = "Error")
        }

        val librarianToLogin = librarianServiceOld.logIn(email,password)
            ?: return ApiResponse(data = null, message = "Wrong username or pass")

        return ApiResponse(data = librarianToLogin, message = "OK")
    }
}