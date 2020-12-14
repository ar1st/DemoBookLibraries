package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.services.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/books"])
class BookController {
    @Autowired
    lateinit var bookService: BookService

    @GetMapping("")
    fun getAllBooks(): List<Book> {
        return bookService.findAll()
    }

    @GetMapping("/{ID}")
    fun getBookById (@PathVariable("ID",required = true) bookId: Long): Book {
        return bookService.findById(bookId) ?: Book(bookId = -1,title = "Not Found", author = null)
    }

    @PostMapping("")
    fun createBook(request: HttpServletRequest, response: HttpServletResponse,
                     @RequestBody data: Book): Book {
        var createdBook: Book?
        if ( data.title == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            println("no title !!!!!")
            return Book(bookId = -1,title = "ADD EMAIL", author = null)
        }
        var bookData = Book(bookId = null, title = data.title,author = null)

        if ( data.author != null) {
            createdBook = bookService.addBook(bookData, data.author!!)
            println("Book added successfully")
            response.status = HttpStatus.ACCEPTED.value()
            return createdBook ?: Book(bookId = -1, "Sth is wrong",author = null)

        } else {
            println("no author!!!!")
            response.status = HttpStatus.BAD_REQUEST.value()
            return Book(bookId = -1,title = "ADD AUTHOR", author = null)
        }

    }
}