package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.User
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

    fun getAllUsers(): ApiResponse<List<User>, String> {
        val allUsers = userService.findAll()
        return ApiResponse(data = allUsers, message = "OK")
    }

    fun getUserById(userId: Long, response: HttpServletResponse): ApiResponse<User, String> {
        val userToReturn = userService.findById(userId)

        return if ( userToReturn != null ) {
            ApiResponse(data=userToReturn, message = "OK")
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No user with such id.")
        }
    }

    fun createUser(data: User, response: HttpServletResponse): ApiResponse<User, String> {
        if (data.email == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Insert an email.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = userService.save(data), message = "OK" )
    }

    fun updateUser(data: User, response: HttpServletResponse, ID: Long): ApiResponse<User, String> {
        if (ID != data.userId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Error: User id does not match Path id.")
        }

      //  val authorToUpdate = authorService.findById(data.authorId ?: -1)
        val userToUpdate = userService.findById(data.userId ?: -1)
        if (  userToUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Error: No user with such id found.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = userService.save(data), message = "OK" )
    }

    fun deleteById(userId: Long, response: HttpServletResponse): ApiResponse<String, String> {
        val matchedUser = userService.findById(userId) ?: return ApiResponse(data = null, message = "OK")

        userService.deleteById(userId)
        return ApiResponse( data = null, message = "OK")
    }

}