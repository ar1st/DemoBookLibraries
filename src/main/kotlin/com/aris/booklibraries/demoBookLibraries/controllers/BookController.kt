package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.services.HasBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class BookController {
    @Autowired
    lateinit var authorExecutor: AuthorExecutor
    @Autowired
    lateinit var bookExecutor: BookExecutor
    @Autowired
    lateinit var hasBookService: HasBookService


    @GetMapping("/books/add/writtenBy/{authorId}")
    fun addBook(@ModelAttribute book: Book, model: Model,@PathVariable("authorId") authorId: Long): String {
        model.addAttribute("book",book)
        model.addAttribute("author", authorExecutor.getAuthorById(authorId,null) )
        return "books/add"
    }

    @PostMapping("/books/add/writtenBy/{authorId}")
    fun submitBook(@ModelAttribute("book") book: Book, model: Model,@PathVariable("authorId") authorId: Long): String {
        val bookToAdd = Book(null,book.title, authorExecutor.getAuthorById(authorId,null).data)
        val response =  bookExecutor.createBook(bookToAdd,null)
        model.addAttribute("message",response.message)
        return "main"
    }

    @GetMapping("/books/booksToAddToLibrary/libraries/{ID}")
    fun showAllBooksToAddToLibrary(model: Model, @PathVariable("ID") libraryId: Long): String {
        val books = bookExecutor.getAllBooksNotInSpecificLibrary(libraryId)
        model.addAttribute("books", books)
        return "books/booksToAddToLibrary"
    }

    @GetMapping("/books/{bookId}/addingToLibrary/libraries/{libraryId}")
    fun addingBookToLibrary(@PathVariable("bookId",required = true) bookId: Long,
                            @PathVariable("libraryId",required = true) libraryId: Long,
                            model: Model): String{
//        val user = userExecutor.getUserById( userId.toLong(),null)
//        val hasBook = hasBookService.getById( hasBookId.toLong() )
        val p = hasBookService.addBook(libraryId.toLong(),bookId.toLong(),10)
        model.addAttribute("message","Book added.")
        return "main"
    }




}