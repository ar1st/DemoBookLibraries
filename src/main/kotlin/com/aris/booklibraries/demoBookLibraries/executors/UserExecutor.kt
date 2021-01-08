package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Borrows
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse

@Component
class UserExecutor {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var bookService: BookService
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var hasBookService: HasBookService
    @Autowired
    lateinit var borrowsService: BorrowsService

    fun getAllUsers(): ApiResponse<List<User>, String> {
        val allUsers = userService.findAll()
        return ApiResponse(data = allUsers, message = "OK")
    }

    fun getUserById(userId: Long, response: HttpServletResponse?): ApiResponse<User, String> {
        val userToReturn = userService.findById(userId)

        return if ( userToReturn != null ) {
            ApiResponse(data=userToReturn, message = "OK")
        } else {
            response?.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No user with such id.")
        }
    }

    fun createUser(data: User, response: HttpServletResponse?): ApiResponse<User, String> {
        if (data.email == null) {
            response?.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Insert an email.")
        }

        response?.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = userService.save(data), message = "OK" )
    }

    fun logIn(email: String,password: String): ApiResponse<User,String>{
        if ( email.isEmpty() || password.isEmpty()) {
            return ApiResponse(data = null, message = "Error")
        }

        val userToLogin = userService.logIn(email,password)
            ?: return ApiResponse(data = null, message = "Wrong username or pass")

        return ApiResponse(data = userToLogin, message = "OK")
    }

    fun updateUser(data: User, response: HttpServletResponse, ID: Long): ApiResponse<User, String> {
        if (ID != data.userId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Error: User id does not match Path id.")
        }

      //  val authorToUpdate = authorService.findById(data.authorId ?: -1)
        val userToUpdate = userService.findById(data.userId ?: -1)
        if (  userToUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Error: No user with such id found.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = userService.save(data), message = "OK" )
    }

    fun deleteById(userId: Long, response: HttpServletResponse): ApiResponse<String, String> {
        val matchedUser = userService.findById(userId) ?: return ApiResponse(data = null, message = "OK")

        userService.deleteById(userId)
        return ApiResponse( data = null, message = "OK")
    }

    fun borrowBook(data: HasBook, ID: Long, response: HttpServletResponse?): ApiResponse<Borrows, String> {
        val userToBorrow =userService.findById(ID)
            ?: return ApiResponse(data = null, message = "Error: No user with such id.")

        val bookToAdd = bookService.findById(data.book.bookId?:-1)
            ?: return ApiResponse(data = null, message = "Error: No book with such id.")

        val libraryToBorrowFrom = libraryService.findById(data.library.libraryId?:-1)
            ?: return ApiResponse(data = null, message = "Error: No library with such id.")

        val bookExistsInLibrary = hasBookService
             .isBookInSpecificLibrary( libraryToBorrowFrom.libraryId!!, bookToAdd.bookId!!)
            ?: return ApiResponse(data = null, message = "Error: The book isn't in this library.")

        val oke = borrowsService.isBorrowed(userToBorrow.userId!!,bookExistsInLibrary.hasBookId!!)

        if ( oke == null ) {
            val quantity = hasBookService.getQuantity(libraryToBorrowFrom.libraryId!!, bookToAdd.bookId!!)
            if ( quantity <= 0 ) return ApiResponse(data = null, message = "Error: No available copies of this book.")

            hasBookService.subtractQuantityByOne(libraryToBorrowFrom.libraryId!!, bookToAdd.bookId!!)

            val borrows = Borrows (null,userToBorrow, bookExistsInLibrary, LocalDate.now(),null)
            val saved = borrowsService.save(borrows)
            return ApiResponse(data = saved, message = "OK")
        }
        return ApiResponse(data = null, message = "The book is already borrowed by this user.")
    }

    fun returnBook(data: HasBook,
                   response: HttpServletResponse?, ID: Long): ApiResponse<String, String> {
        val bookExistsInLibrary = hasBookService
            .isBookInSpecificLibrary( data.library.libraryId?:-1, data.book.bookId?:-1)
            ?: return ApiResponse(data = null, message = "Error: The book isn't in this library.")

        val borrows = borrowsService.isBorrowed(ID,bookExistsInLibrary.hasBookId?:-1)
            ?: return ApiResponse(data = null, message = "Error: This book was already returned.")

        borrowsService.returnBook(borrows.borrowsId!!,LocalDate.of(2020,2,2))
        hasBookService.addQuantityByOne(data.library.libraryId!!,data.book.bookId!!)
        return ApiResponse(data = null, message = "OK")
    }
}