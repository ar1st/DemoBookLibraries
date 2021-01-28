package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.models.BorrowDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {
    @Autowired
    lateinit var bookExecutor: BookExecutor

    @GetMapping("/books/{bookId}/details")
    fun showBookDetails(@PathVariable("bookId") bookId: String): BorrowDetails {
        return bookExecutor.findBookDetailsById(bookId)
    }

}