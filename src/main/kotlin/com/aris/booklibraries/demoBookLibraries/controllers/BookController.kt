package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/books"])
class BookController {
    @Autowired
    lateinit var bookMiddleMan: BookExecutor

    @GetMapping("")
    fun getAllBooks(): ApiResponse<List<Book>, String> {
        return bookMiddleMan.getAllBooks()
    }

    @GetMapping("/{ID}")
    fun getBookById (@PathVariable("ID",required = true) bookId: Long, response: HttpServletResponse): ApiResponse<Book, String> {
        return bookMiddleMan.getBookById(bookId,response)
    }

    @PostMapping("")
    fun createBook( response: HttpServletResponse,
                     @RequestBody data: Book): ApiResponse<Book, String> {
        return bookMiddleMan.createBook(data,response)
    }

    @DeleteMapping("/{ID}")
    fun deleteByID(@PathVariable(value = "ID", required = true) bookId:Long, respose:HttpServletResponse) {
        bookMiddleMan.deleteById(bookId)
    }

    @DeleteMapping("/deletefromauthor/{ID}")
    fun deleteByAuthor(@PathVariable(value="ID",required = true) authorId: Long) {
        bookMiddleMan.deleteByAuthor(authorId)
    }
}