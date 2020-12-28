package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.BookService
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class LibraryExecutor {
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var bookService: BookService

    fun getAllLibraries(): ApiResponse<List<Library>,String> {
        val allLibraries = libraryService.findAll()
        return if ( allLibraries != null) {
            ApiResponse(data = allLibraries, message = "OK")
        } else {
            return ApiResponse(data = null, message = "Error: Sth went wrong.")
        }
    }

    fun getLibraryById(libraryId: Long,response: HttpServletResponse): ApiResponse<Library,String> {
        val libraryToReturn = libraryService.findById(libraryId)

        return if ( libraryToReturn != null ) {
            ApiResponse(data=libraryToReturn,message = "OK")
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No library with such id.")
        }
    }


    fun getBooksByLibrary(libraryId: Long, response: HttpServletResponse) : ApiResponse<List<Book>,String> {
        val matchedLibrary = libraryService.findById(libraryId)

        return if ( matchedLibrary != null) {
            val allBooksFromLibrary = libraryService.findAllBooks(matchedLibrary.libraryId!!)
            ApiResponse(data = allBooksFromLibrary, message = "OK")
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No library with such id.")
        }
    }

    fun deleteAllBooksFromLibrary(libraryId: Long, response: HttpServletResponse) : ApiResponse<String,String> {
        val matchedLibrary = libraryService.findById( libraryId)
        if (matchedLibrary != null ) {
            libraryService.deleteAllBooks(matchedLibrary.libraryId!!)
            return ApiResponse(data = null, message = "OK")
        }
        response.status = HttpStatus.BAD_REQUEST.value()
        return ApiResponse(data = null, message = "Error: No library with such id.")
    }

    fun deleteBookFromLibraries(bookId: Long, response: HttpServletResponse) : ApiResponse<String,String> {
        val matchedBook = bookService.findById(bookId)

        if ( matchedBook != null) {
            libraryService.removeBookFromLibraries(bookId)
            return ApiResponse(data = null, message = "OK")
        }

        response.status = HttpStatus.BAD_REQUEST.value()
        return ApiResponse(data = null, message = "Error: No book with such id.")
    }

    fun deleteById(libraryId: Long, response: HttpServletResponse): ApiResponse<String, String> {
        val libraryToDelete = libraryService.findById(libraryId)

        if ( libraryToDelete != null) {
            libraryService.deleteAllBooks(libraryId)
            libraryService.deleteById(libraryId)
        }

        return ApiResponse(data = null, message = "OK")
    }
}