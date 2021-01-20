package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.executors.UserExecutorOld
import com.aris.booklibraries.demoBookLibraries.models.Borrows
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.UserOld
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/usersrest")
class UserControllerRest {
    @Autowired
    lateinit var userExecutorOld: UserExecutorOld

    @GetMapping("")
    fun getAllUsers(): ApiResponse<List<UserOld>, String> {
        return userExecutorOld.getAllUsers()
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) userId: Long,
                       response: HttpServletResponse): ApiResponse<UserOld, String> {
        return userExecutorOld.getUserById(userId, response)
    }

    @PostMapping("")
    fun createUser(@Valid @RequestBody data: UserOld,
                   response: HttpServletResponse): ApiResponse<UserOld,String> {
        return userExecutorOld.createUser(data, response)
    }

//    @PostMapping("/{ID}/books")
//    fun borrowBook(@RequestBody data: HasBook,
//                        response: HttpServletResponse, @PathVariable ID: Long): ApiResponse<Borrows,String>{
//        return userExecutorOld.borrowBook(data,ID,response,)
//    }

//    @DeleteMapping("/{ID}/books")
//    fun returnBook(@RequestBody data: HasBook,
//                   response: HttpServletResponse, @PathVariable ID: Long): ApiResponse<String,String>{
//        return userExecutorOld.returnBook(data,response,ID)
//    }

    @PutMapping("/{ID}")
    fun updateAuthor(@RequestBody data: UserOld,
                     response: HttpServletResponse, @PathVariable ID: Long):  ApiResponse<UserOld,String> {
        return userExecutorOld.updateUser(data, response, ID)
    }

    @DeleteMapping("/{ID}")
    fun deleteById(@PathVariable("ID",required = true) userId: Long,
                   response: HttpServletResponse): ApiResponse<String,String>{
        return userExecutorOld.deleteById(userId,response)
    }
}