package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.BorrowDetails
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AccountService
import com.aris.booklibraries.demoBookLibraries.services.BookService
import com.aris.booklibraries.demoBookLibraries.services.BorrowsService
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
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var borrowsService: BorrowsService

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

        val bookData = Book(null, data.title, null,null,null,null,null)
        return if (data.author != null) {
            val createdBook = bookService.addBook(bookData, data.author!!)
            response?.status = HttpStatus.ACCEPTED.value()
            ApiResponse(data = createdBook, message = "Book created.")

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

    fun findBorrowedBooksByUser(username: String): MutableList<BorrowDetails> {
        val listToReturn = mutableListOf<BorrowDetails>()
        val loggedUser = accountService.findByEmail(username)
        val borrow = borrowsService.getBorrowsDetails(loggedUser?.accountId!!)
        for (element: String in borrow) {
            val parts = element.split(",")
            val details = BorrowDetails(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6],parts[7],parts[8],parts[9],parts[10],parts[11])
            listToReturn.add(details)
        }

        return listToReturn
    }

    fun findBookDetailsById(bookId: String): BorrowDetails {
        val bookDetails = bookService.getBookDetails(bookId.toLong())
        val parts = bookDetails.split(",")
        parts[2].replace("*", ",")
        return BorrowDetails(
            parts[0], parts[1], null, null, null, null, parts[2],
            parts[3], parts[4], parts[5], parts[6], parts[7]
        )

    }
}