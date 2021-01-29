package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.services.LibrarianService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AuthorController {
    @Autowired
    lateinit var authorExecutor: AuthorExecutor
    @Autowired
    lateinit var librarianService: LibrarianService
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor

    @GetMapping("/authors/add")
    fun addAuthor(@ModelAttribute author: Author, model: Model): String {
        model.addAttribute("author",author)
        return "authors/add"
    }

    @PostMapping("/authors/add")
    fun submitForm(@ModelAttribute("author") author: Author, model: Model): String {
        val email = SecurityContextHolder.getContext().authentication.name
        val response = authorExecutor.createAuthor(author, null)
        model.addAttribute("message",response.message)
        val librarian = librarianService.findLibrarianByAccountEmail(email)
                ?: return "sth is very wrong"
        val books = libraryExecutor.getBooksByLibrary(librarian.library?.libraryId!!,null)
        model.addAttribute("books", books)

        return "homepage/homepage-librarian"
    }

    @GetMapping("/authors/authorsToWriteBook")
    fun showAllAuthors(model: Model): String {
        model.addAttribute("authors", authorExecutor.getAllAuthors())
        return "authors/authorsToWriteBook"
    }
}