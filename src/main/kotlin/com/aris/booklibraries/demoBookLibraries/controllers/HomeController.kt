package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.UserExecutorOld
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @Autowired
    lateinit var userExecutorOld: UserExecutorOld
    @Autowired
    lateinit var bookExecutor: BookExecutor

    @RequestMapping("")
    fun showMainPage(): String {
        println()
        return "main"
    }

    @GetMapping("/users")
    fun showAllUsers(model: Model): String {
        model.addAttribute("users", userExecutorOld.getAllUsers())
        return "general/showusers"
    }

    @GetMapping("/books")
    fun showAllBooks(model: Model): String {
        model.addAttribute("books", bookExecutor.getAllBooks())
        return "general/showbooks"
    }
}