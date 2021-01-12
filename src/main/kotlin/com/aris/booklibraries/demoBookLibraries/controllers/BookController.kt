package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.services.BorrowsService
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
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor
    @Autowired
    lateinit var borrowsService: BorrowsService


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
        val isInLibrary = hasBookService.isBookInSpecificLibrary(libraryId, bookId)
        if ( isInLibrary == null) {
            hasBookService.addBook(libraryId, bookId,10)
            model.addAttribute("message","Book added.")
        } else {
            model.addAttribute("message","Book already in library.")
        }

        return "main"
    }

    @GetMapping("/books/showBooksInLibrary/libraries/{ID}")
    fun showAllBookToRemoveFromLibrary(model: Model, @PathVariable("ID") libraryId: Long): String {
        val books = libraryExecutor.getBooksByLibrary(libraryId,null)
        model.addAttribute("books", books)
        return "books/showBooksInLibrary"
    }

    @GetMapping("/books/{bookId}/removingFromLibrary/libraries/{libraryId}")
    fun removingBookFromLibrary(@PathVariable("bookId",required = true) bookId: Long,
                            @PathVariable("libraryId",required = true) libraryId: Long,
                            model: Model): String{

        val isBorrowed = borrowsService.isBookBorrowed(bookId)
        if ( isBorrowed.intValueExact() == 0 ) {
            libraryExecutor.deleteBookFromSpecificLibrary(libraryId, bookId, null)
            model.addAttribute("message", "Book removed.")
        } else {
            model.addAttribute("message", "Book can't be removed. Is borrowed.")
        }
        return "main"
    }
}