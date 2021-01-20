package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import com.aris.booklibraries.demoBookLibraries.models.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AuthorController {
    @Autowired
    lateinit var authorExecutor: AuthorExecutor

    @GetMapping("/authors/add")
    fun addAuthor(@ModelAttribute author: Author, model: Model): String {
        model.addAttribute("author",author)
        return "authors/add"
    }

    @PostMapping("/authors/add")
    fun submitForm(@ModelAttribute("author") author: Author, model: Model): String {
        val response = authorExecutor.createAuthor(author, null)
        model.addAttribute("message",response.message)
        return "homepage/homepage-librarian"
    }

    @GetMapping("/authors/authorsToWriteBook")
    fun showAllAuthors(model: Model): String {
        model.addAttribute("authors", authorExecutor.getAllAuthors())
        return "authors/authorsToWriteBook"
    }
}