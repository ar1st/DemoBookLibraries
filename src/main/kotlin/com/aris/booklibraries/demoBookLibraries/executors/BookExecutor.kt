package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class BookExecutor {
    @Autowired
    lateinit var bookService: BookService
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor

    fun getAllBooks(): ApiResponse< List<Book>,String> {
        val allBooks = bookService.findAll()
        return ApiResponse(data = allBooks, message = "OK")
    }

    fun getBookById(bookId: Long,response: HttpServletResponse?): ApiResponse<Book,String> {
        val bookToReturn = bookService.findById(bookId)

        return if ( bookToReturn != null ) {
            ApiResponse(data=bookToReturn, message = "OK")
        } else {
            response?.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No book with such id.")
        }
    }

    fun getAllBooksNotInSpecificLibrary(libraryId: Long): ApiResponse<List<Book>,String> {
        val matchedLibrary = libraryExecutor.getLibraryById(libraryId,null)

        if (matchedLibrary.data == null) return ApiResponse(null,"Error: No such library")

        val books = bookService.findAllBooksNotInSpecificLibrary(matchedLibrary.data!!.libraryId!!)
        return ApiResponse(books,"OK")
    }

    fun getLibrariesByBook(bookId: Long, response: HttpServletResponse): ApiResponse<List<Library>, String> {
        val matchedBook = bookService.findById(bookId)

        return if ( matchedBook != null) {
            val allBooksFromLibrary = bookService.findAllBooks(matchedBook.bookId!!)
            ApiResponse(data = allBooksFromLibrary, message = "OK")
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No book with such id.")
        }
    }

    //todo fix : author name may not correspond with author id
    fun createBook(data: Book, response: HttpServletResponse?) : ApiResponse<Book,String> {
        if (data.title == null) {
            response?.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "No title added.")
        }

        val bookData = Book(null, data.title, null)
        return if (data.author != null) {
            val createdBook = bookService.addBook(bookData, data.author!!)
            response?.status = HttpStatus.ACCEPTED.value()
            ApiResponse(data = createdBook, message = "OK")

        } else {
            response?.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "No author found")
        }
    }

    fun deleteById(bookId: Long, response: HttpServletResponse): ApiResponse<String,String> {
        val bookToDelete = bookService.findById(bookId)
        if ( bookToDelete != null) {
            bookService.deleteById(bookId)
        }
        return ApiResponse(data = null, message = "OK")
    }


}