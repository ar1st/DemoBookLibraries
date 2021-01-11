package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/booksrest"])
class BookControllerRest {
    @Autowired
    lateinit var bookExecutor: BookExecutor

    @GetMapping("")
    fun getAllBooks(): ApiResponse<List<Book>, String> {
        return bookExecutor.getAllBooks()
    }

    @GetMapping("/{ID}")
    fun getBookById (@PathVariable("ID",required = true) bookId: Long, response: HttpServletResponse): ApiResponse<Book, String> {
        return bookExecutor.getBookById(bookId,response)
    }

    @GetMapping("/{ID}/libraries")
    fun getLibrariesWhereBookIs(@PathVariable("ID",required = true) bookId: Long,
                                response: HttpServletResponse): ApiResponse<List<Library>, String> {
        return bookExecutor.getLibrariesByBook(bookId,response)
    }

    @PostMapping("")
    fun createBook( response: HttpServletResponse,
                     @RequestBody data: Book): ApiResponse<Book, String> {
        return bookExecutor.createBook(data,response)
    }

    @DeleteMapping("/{ID}")
    fun deleteByID(@PathVariable(value = "ID", required = true) bookId:Long,
                   response:HttpServletResponse): ApiResponse<String,String> {
        return bookExecutor.deleteById(bookId,response)
    }
}