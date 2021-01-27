package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.services.BorrowsService
import com.aris.booklibraries.demoBookLibraries.services.HasBookService
import com.aris.booklibraries.demoBookLibraries.services.LibrarianService
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
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor
    @Autowired
    lateinit var borrowsService: BorrowsService
    @Autowired
    lateinit var librarianService: LibrarianService


    @GetMapping("/books/add/writtenBy/{authorId}")
    fun addBook(@ModelAttribute book: Book, model: Model,@PathVariable("authorId") authorId: Long): String {
        model.addAttribute("book",book)
        model.addAttribute("author", authorExecutor.getAuthorById(authorId,null) )
        return "books/add"
    }

    @PostMapping("/books/add/writtenBy/{authorId}")
    fun submitBook(@ModelAttribute("book") book: Book, model: Model,@PathVariable("authorId") authorId: Long): String {
        val bookToAdd = Book(null,book.title, authorExecutor.getAuthorById(authorId,null).data,null,null,null,null)
        val response =  bookExecutor.createBook(bookToAdd,null)
        model.addAttribute("message",response.message)
        return "homepage/homepage-librarian"
    }

    @GetMapping("/books/booksToAddToLibrary/libraries/{email}")
    fun showAllBooksToAddToLibrary(model: Model, @PathVariable("email") email: String): String {
        val librarian = librarianService.findLibrarianByAccountEmail(email)
            ?: return "sth is very wrong"

        val books = bookExecutor.getAllBooksNotInSpecificLibrary(librarian.library?.libraryId!!)
        model.addAttribute("books", books)
        model.addAttribute("libraryId",librarian.library?.libraryId!!)
        return "books/booksToAddToLibrary"
    }

    @GetMapping("/books/{bookId}/addingToLibrary/libraries/{libraryId}")
    fun addingBookToLibrary(@PathVariable("bookId",required = true) bookId: Long,
                            @PathVariable("libraryId",required = true) libraryId: String,
                            model: Model): String{
        val library = libraryExecutor.getLibraryById( libraryId.toLong(),null)

        val isInLibrary = hasBookService.isBookInSpecificLibrary(library.data?.libraryId!!, bookId)
        if ( isInLibrary == null) {
            hasBookService.addBook(library.data?.libraryId!!, bookId,10)
            model.addAttribute("message","Book added.")
        } else {
            model.addAttribute("message","Book already in library.")
        }

        return "homepage/homepage-librarian"
    }

    @GetMapping("/books/showBooksInLibrary/libraries/{email}")
    fun showAllBookToRemoveFromLibrary(model: Model, @PathVariable("email") email: String): String {
        val librarian = librarianService.findLibrarianByAccountEmail(email)
            ?: return "sth is very wrong"

        val books = libraryExecutor.getBooksByLibrary(librarian.library?.libraryId!!,null)
        model.addAttribute("books", books)
        return "books/showBooksInLibrary"
    }

    @GetMapping("/books/{bookId}/removingFromLibrary/libraries/{email}")
    fun removingBookFromLibrary(@PathVariable("bookId",required = true) bookId: Long,
                            @PathVariable("email",required = true) email: String,
                            model: Model): String{
        val librarian = librarianService.findLibrarianByAccountEmail(email)
            ?: return "sth is very wrong"

        val isBorrowed = borrowsService.isBookBorrowed(bookId)
        if ( isBorrowed.intValueExact() == 0 ) {
            libraryExecutor.deleteBookFromSpecificLibrary(librarian.library?.libraryId!!, bookId, null)
            model.addAttribute("message", "Book removed.")
        } else {
            model.addAttribute("message", "Book can't be removed. Is borrowed.")
        }
        return "homepage/homepage-librarian"
    }

    @GetMapping("book/details")
    fun showBookDetails(model: Model): Model {
         model.addAttribute("book", "katina")
        return model;
    }
}