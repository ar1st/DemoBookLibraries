package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.models.UserOld
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class UserExecutor {
    @Autowired
    lateinit var userService: UserService

    fun createUser(data: User, response: HttpServletResponse?): ApiResponse<User, String> {
        if (data.firstName == null || data.firstName!!.isEmpty()) {
            response?.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Insert first name.")
        }

        if (data.lastName == null || data.lastName!!.isEmpty()) {
            response?.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Insert last name.")
        }

        response?.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = userService.save(data), message = "OK" )
    }
}