package com.aris.booklibraries.demoBookLibraries.controllers

import org.springframework.web.bind.annotation.*
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping(value = ["/authors"])
class AuthorController {
    @Autowired
    lateinit var authorService: AuthorService

    @GetMapping("")
    fun getAllAuthors(): List<Author> {
        return authorService.findAll()
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) authorId: Long): Author{
        return authorService.findById(authorId) ?: Author(-1,"rkaj","dwada","dwa")
    }



}