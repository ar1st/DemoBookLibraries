package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.UserExecutor
import com.aris.booklibraries.demoBookLibraries.models.BorrowDetails
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.services.BorrowsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
class HomeController {
    @Autowired
    lateinit var userExecutor: UserExecutor
    @Autowired
    lateinit var bookExecutor: BookExecutor
    @Autowired
    lateinit var borrowsService: BorrowsService

    @RequestMapping("/")
    fun showMainPage(): String {
        return "main"
    }

    @GetMapping("/users")
    fun showAllUsers(model: Model): String {
        model.addAttribute("users", userExecutor.getAllUsers())
        return "general/showusers"
    }

    @GetMapping("/books")
    fun showAllBooks(model: Model): String {
        model.addAttribute("books", bookExecutor.getAllBooks())
        return "general/showbooks"
    }


}