package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Account
import com.aris.booklibraries.demoBookLibraries.models.Authority
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AuthorityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class AuthorityExecutor {
    @Autowired
    lateinit var authorityService: AuthorityService

    fun createAuthority(data: Authority, response: HttpServletResponse?): ApiResponse<Authority, String> {
        response?.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = authorityService.save(data), message = "OK" )
    }
}