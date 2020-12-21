package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import com.aris.booklibraries.demoBookLibraries.services.CityService
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/libraries"])
class LibraryController {
    @Autowired
    lateinit var libraryService: LibraryService

    @GetMapping("")
    fun getAllLibraries(): List<Library> {
        return libraryService.findAll()
    }

    @GetMapping(value = ["/{ID}"])
    fun getById(@PathVariable("ID",required = true) libraryId: Long) : Library? {
        return libraryService.findById(libraryId)
    }

    @GetMapping(value=["/{ID}/books"])
    fun getBooksByLibrary(@PathVariable("ID",required = true) libraryId: Long) : List<Book> {

        val library = libraryService.findById(libraryId)
        if ( library != null ){
            return libraryService.findAllBooks( library!!)
        }

        return emptyList()
    }

    //@DeleteMapping("/{ID}/books")
    @RequestMapping(
        value = ["/{ID}/books"],
        produces = ["application/json"],
        method = [RequestMethod.DELETE])
    fun deleteAllBooks(@PathVariable("ID", required = true) libraryId: Long) {
        val matchedLibrary = libraryService.findById( libraryId)
        if (matchedLibrary != null ) {
            libraryService?.deleteAllBooks(matchedLibrary)
        }
    }

    @DeleteMapping("/deletebook/{ID}")
    fun deleteBookById(@PathVariable("ID", required = true) bookId: Long) {
        libraryService.deleteBookById(bookId = bookId)
    }


}