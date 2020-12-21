package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import com.aris.booklibraries.demoBookLibraries.services.BookService
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class BookExecutor {
    @Autowired
    lateinit var bookService: BookService
    @Autowired
    lateinit var authorService: AuthorService
    @Autowired
    lateinit var libraryService: LibraryService

    fun getAllBooks(): ApiResponse< List<Book>,String> {
        val allBooks = bookService.findAll()

        return if (allBooks != null) {
            ApiResponse(data = allBooks)
        } else {
            ApiResponse(null,message = "Something went wrong")
        }
    }

    fun getBookById(bookId: Long,response: HttpServletResponse): ApiResponse<Book,String> {
        val bookToReturn = bookService.findById(bookId)

        return if ( bookToReturn != null ) {
            ApiResponse(data=bookToReturn)
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "No book with such id.")
        }
    }

    //todo fix : author name may not correspond with author id
    fun createBook(data: Book, response: HttpServletResponse) : ApiResponse<Book,String> {
        if (data.title == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "No title added.")
        }

        val bookData = Book(null, data.title, null)
        if (data.author != null) {
            val createdBook = bookService.addBook(bookData, data.author!!)
            response.status = HttpStatus.ACCEPTED.value()
            return ApiResponse(data = createdBook)

        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "No author found")
        }
    }

    fun deleteById(bookId: Long) {
        libraryService?.deleteBookById(bookId)
        bookService.deleteById(bookId)
    }

    fun deleteByAuthor(authorId: Long) {
        //get the id of the books with author id
        //delete them from has_books
        //then finally delete book


        bookService.deleteByAuthor(authorId)
    }


}