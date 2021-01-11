package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibrarianExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class AddBookToLibraryController {
    @Autowired
    lateinit var bookExecutor: BookExecutor

    @GetMapping("/addbook/showBooks")
    fun showAllBooks(model: Model): String {
        model.addAttribute("books", bookExecutor.getAllBooks() )
        return "addbooktolibrary/showbooks"

    }
}