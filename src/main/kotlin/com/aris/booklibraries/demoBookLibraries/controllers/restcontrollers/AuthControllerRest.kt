package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.executors.UserExecutorOld
import com.aris.booklibraries.demoBookLibraries.models.UserOld
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
class AuthControllerRest {
    @Autowired
    lateinit var userExecutorOld: UserExecutorOld

    @GetMapping("/login/{email}/{password}")
    fun checkUserLogin(@PathVariable("email") email:String, @PathVariable("password") password:String):
            ApiResponse<UserOld,String> {
        val tryToLog = userExecutorOld.logIn( email, password)
        if (tryToLog.data != null) {
            return ApiResponse(tryToLog.data,tryToLog.message)
        }

        return ApiResponse(null,tryToLog.message)
    }
}