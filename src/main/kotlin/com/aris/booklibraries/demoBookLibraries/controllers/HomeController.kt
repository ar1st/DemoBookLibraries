package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.UserExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @Autowired
    lateinit var userExecutor: UserExecutor
    @RequestMapping("/")
    fun showMainPage(): String {
        return "main"
    }

    @GetMapping("/userss")
    fun showAllUsers(model: Model): String {
        model.addAttribute("users", userExecutor.getAllUsers())
        return "showusers"
    }

}