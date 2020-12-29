package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.BookService
import com.aris.booklibraries.demoBookLibraries.services.HasBookService
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletResponse

@Component
class LibraryExecutor {
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var bookService: BookService
    @Autowired
    lateinit var hasBookService: HasBookService

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

    fun createLibrary(data: Library, response: HttpServletResponse): ApiResponse<Library, String> {
        if (data.name == null) {
            // response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "City name required!")
        }

        val libraryData = Library(null,data.name,null)
        return if (data.city != null) {
            val createdLibrary = libraryService.addLibrary(libraryData, data.city!!)
            response.status = HttpStatus.ACCEPTED.value()
            ApiResponse(data = createdLibrary, message = "OK")
        } else {
            // response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "No city found")
        }
    }

    fun partiallyUpdateLibrary(response: HttpServletResponse,
                               hMap: HashMap<String, Any>, ID: Long): ApiResponse<Library, String> {
        val libraryId =  hMap["libraryId"].toString().toLong()

        if ( ID != libraryId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Library id does not match Path id.")
        }

        val libraryToPartiallyUpdate = libraryService.findById(libraryId)
        if (  libraryToPartiallyUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "Error: No library with such id found.")
        }

        libraryToPartiallyUpdate.patch(hMap)
        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = libraryService.save(libraryToPartiallyUpdate), message = "OK")
    }

    fun addBookToSpecificLibrary(libraryId: Long, data: HasBook,
                                 response: HttpServletResponse): ApiResponse<Book, String> {
        if ( libraryId != data.library.libraryId )
            return ApiResponse(data = null, "Error: Library Id doesn't not match Path Id.")

        libraryService.findById(libraryId)
            ?: return ApiResponse(data = null, "Error: No library with such id")

        bookService.findById( data.book.bookId ?: -1)
            ?: return ApiResponse(data = null, "Error: No book with such id")

        //todo want to check if library already has book
        val temp = hasBookService.isBookInSpecificLibrary(data.library.libraryId!!, data.book.bookId!!)
        if (temp == null ) {
            hasBookService.addBook(libraryId, data.book.bookId!!, data.quantity)
            return ApiResponse(data = null, "The book was added successfully")
        }

//        if ( data.bookId == null) {
//            //we want to create the book
//            if ( data.title == null ) {
//                return ApiResponse(data = null, "Error: Add title to the book")
//            }
//
//            if ( data.author == null ) {
//                return  ApiResponse(data = null, "Error: Add author to book")
//            } else {
//                //check if author is not empty
//            }
//
//            val book = bookService.addBook( Book(null,data.title,null), data.author!!)
//            libraryService.addBook( libraryId, book!!)
//            return ApiResponse(data = null, "Both the book and the library exists.")
//        }

     //   libraryService.addBook( libraryId, data.book.bookId!!, data.quantity)
        return ApiResponse(data = null, "Book already exists.")
    }


    fun deleteAllBooksFromSpecificLibrary(libraryId: Long, response: HttpServletResponse) : ApiResponse<String,String> {
        val matchedLibrary = libraryService.findById( libraryId)
        if (matchedLibrary != null ) {
            libraryService.deleteAllBooksFromSpecificLibrary(matchedLibrary.libraryId!!)
            return ApiResponse(data = null, message = "OK")
        }
        response.status = HttpStatus.BAD_REQUEST.value()
        return ApiResponse(data = null, message = "Error: No library with such id.")
    }

    fun deleteBookFromLibraries(bookId: Long, response: HttpServletResponse) : ApiResponse<String,String> {
        val matchedBook = bookService.findById(bookId)

        if ( matchedBook != null) {
            libraryService.removeBookFromAllLibraries(bookId)
            return ApiResponse(data = null, message = "OK")
        }

        response.status = HttpStatus.BAD_REQUEST.value()
        return ApiResponse(data = null, message = "Error: No book with such id.")
    }

    fun deleteById(libraryId: Long, response: HttpServletResponse): ApiResponse<String, String> {
        val libraryToDelete = libraryService.findById(libraryId)

        if ( libraryToDelete != null) {
            libraryService.deleteAllBooksFromSpecificLibrary(libraryId)
            libraryService.deleteById(libraryId)
        }

        return ApiResponse(data = null, message = "OK")
    }

    fun deleteBookFromSpecificLibrary(libraryId: Long, bookId: Long,
                                      response: HttpServletResponse): ApiResponse<String, String> {
        libraryService.findById(libraryId) ?: return ApiResponse(data = null, "No such library.")

        bookService.findById(bookId) ?: return ApiResponse(data = null, "No such book.")

        val allBooksFromLibrary = libraryService.findAllBooks(libraryId)
        val book: Book = allBooksFromLibrary.firstOrNull { it.bookId == bookId }
            ?: return ApiResponse(data = null, "The book isn't in this library")

        libraryService.removeBookFromSpecificLibrary(libraryId,bookId)
        return ApiResponse(data = null, "OK")
    }
}