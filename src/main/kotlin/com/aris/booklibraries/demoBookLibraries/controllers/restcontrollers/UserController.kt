package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.executors.UserExecutor
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Borrows
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/usersrest")
class UserController {
    @Autowired
    lateinit var userExecutor: UserExecutor

    @GetMapping("")
    fun getAllUsers(): ApiResponse<List<User>, String> {
        return userExecutor.getAllUsers()
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) userId: Long,
                       response: HttpServletResponse): ApiResponse<User, String> {
        return userExecutor.getUserById(userId, response)
    }

    @PostMapping("")
    fun createUser(@Valid @RequestBody data: User,
                     response: HttpServletResponse): ApiResponse<User,String> {
        return userExecutor.createUser(data, response)
    }

    @PostMapping("/{ID}/books")
    fun borrowBook(@RequestBody data: HasBook,
                        response: HttpServletResponse, @PathVariable ID: Long): ApiResponse<Borrows,String>{
        return userExecutor.borrowBook(data,ID,response,)
    }

    @DeleteMapping("/{ID}/books")
    fun returnBook(@RequestBody data: HasBook,
                   response: HttpServletResponse, @PathVariable ID: Long): ApiResponse<String,String>{
        return userExecutor.returnBook(data,response,ID)
    }

    @PutMapping("/{ID}")
    fun updateAuthor( @RequestBody data: User,
                      response: HttpServletResponse, @PathVariable ID: Long):  ApiResponse<User,String> {
        return userExecutor.updateUser(data, response, ID)
    }

    @DeleteMapping("/{ID}")
    fun deleteById(@PathVariable("ID",required = true) userId: Long,
                   response: HttpServletResponse): ApiResponse<String,String>{
        return userExecutor.deleteById(userId,response)
    }
}