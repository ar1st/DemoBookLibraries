package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import com.aris.booklibraries.demoBookLibraries.services.CityService
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/libraries"])
class LibraryController {
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor

    @GetMapping("")
    fun getAllLibraries(): ApiResponse<List<Library>, String> {
        return libraryExecutor.getAllLibraries()
    }

    @GetMapping(value = ["/{ID}"])
    fun getById(@PathVariable("ID",required = true) libraryId: Long,
                response: HttpServletResponse) : ApiResponse<Library, String> {
        return libraryExecutor.getLibraryById(libraryId,response)
    }

    @GetMapping(value=["/{ID}/books"])
    fun getBooksByLibrary(@PathVariable("ID",required = true) libraryId: Long,
                response: HttpServletResponse) : ApiResponse<List<Book>, String> {

        return libraryExecutor.getBooksByLibrary(libraryId,response)
    }

    @DeleteMapping("/{ID}")
    fun deleteById(@PathVariable("ID", required = true) libraryId: Long,
                   response: HttpServletResponse) : ApiResponse<String,String>{
        return libraryExecutor.deleteById(libraryId,response)
    }

    @DeleteMapping("/{ID}/books")
    fun deleteAllBooks(@PathVariable("ID", required = true) libraryId: Long,
                       response: HttpServletResponse) : ApiResponse<String,String>{
        return libraryExecutor.deleteAllBooksFromLibrary(libraryId,response)
    }

    @DeleteMapping("/deletebook/{ID}")
    fun deleteBookById(@PathVariable("ID", required = true) bookId: Long,
                       response: HttpServletResponse): ApiResponse<String, String> {
        return libraryExecutor.deleteBookFromLibraries(bookId, response)
    }
}