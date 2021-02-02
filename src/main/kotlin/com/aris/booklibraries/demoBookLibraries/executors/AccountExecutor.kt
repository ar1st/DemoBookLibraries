package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.*
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.registration.token.ConfirmationToken
import com.aris.booklibraries.demoBookLibraries.registration.token.ConfirmationTokenService
import com.aris.booklibraries.demoBookLibraries.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletResponse

@Component
class AccountExecutor {
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var bookService: BookService
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var hasBookService: HasBookService
    @Autowired
    lateinit var borrowsService: BorrowsService
    @Autowired
    lateinit var librarianService: LibrarianService
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var confirmationTokenService: ConfirmationTokenService

    fun createAccountAndUser(registrationDetails: RegistrationDetails, response: HttpServletResponse?): ApiResponse<Account, String> {

        if ( registrationDetails.email.isNullOrEmpty() || registrationDetails.password.isNullOrEmpty()
            ||registrationDetails.firstName.isNullOrEmpty() ||registrationDetails.lastName.isNullOrEmpty() ){
            return ApiResponse(null, "Fill all the fields.")
        }

        if ( accountService.findByEmail(registrationDetails.email!!) != null)
             return ApiResponse(data=null,message="Error: Email already exists.")

        val encoder = BCryptPasswordEncoder(10)
        val encodedPass = encoder.encode(registrationDetails.password)
        val accountToCreate = Account( null,registrationDetails.email,encodedPass,1,Role.USER.value)

        val createdAccount = accountService.save(accountToCreate)
            ?: return ApiResponse(data=null,message="Something went wrong. Try again later.")

        val userToCreate = User(null,registrationDetails.firstName,registrationDetails.lastName,createdAccount)
        val createdUser = userService.save(userToCreate)

        response?.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(createdAccount,"You can login now :)")
    }

    fun createAccountAndLibrarian(registrationDetails: RegistrationDetails, response: HttpServletResponse?): ApiResponse<Account, String> {

        if ( accountService.findByEmail(registrationDetails.email!!) != null)
            return ApiResponse(data=null,message="Error: Email already exists.")

        val encoder = BCryptPasswordEncoder(10)
        val encodedPass = encoder.encode(registrationDetails.password)
        val accountToCreate = Account( null,registrationDetails.email,encodedPass,1,Role.LIBRARIAN.value)
        val createdAccount = accountService.save(accountToCreate)

        val librarianToCreate = Librarian(null,registrationDetails.firstName,registrationDetails.lastName,
                                                registrationDetails.library,createdAccount)
        val createdLibrarian = librarianService.save(librarianToCreate)

        response?.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = accountService.save(createdAccount!!), message = "OK" )
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun signUpAccount(registrationDetails: RegistrationDetails): String {
        if ( accountService.findByEmail(registrationDetails.email!!) != null)
            return "Error: Email already exists"

        val encoder = BCryptPasswordEncoder(10)
        val encodedPass = encoder.encode(registrationDetails.password)
        val accountToCreate = Account( null,registrationDetails.email,encodedPass,0,Role.USER.value)

        val createdAccount = accountService.save(accountToCreate)
                ?: return "Error: Sth went wrong. Try again later."

        val token = UUID.randomUUID().toString()
        val confirmationToken = ConfirmationToken(
                null,
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null,
                createdAccount
                )

        confirmationTokenService.save(confirmationToken)
        val userToCreate = User(null,registrationDetails.firstName,registrationDetails.lastName,createdAccount)
        userService.save(userToCreate)

        return token
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun findByUsername(username: String): ApiResponse<Account,String> {
        val accountToReturn = accountService.findByEmail(username)
            ?: return ApiResponse(null,"User not found")

        return ApiResponse(accountToReturn,"OK")
    }

    fun borrowBook(data: HasBook, username: String, response: HttpServletResponse?): ApiResponse<Borrows, String> {
        val accountToBorrow =accountService.findByEmail(username)
            ?: return ApiResponse(data = null, message = "Error: No account with such username.")

        val bookToAdd = bookService.findById(data.book?.bookId?:-1)
            ?: return ApiResponse(data = null, message = "Error: No book with such id.")

        val libraryToBorrowFrom = libraryService.findById(data.library?.libraryId?:-1)
            ?: return ApiResponse(data = null, message = "Error: No library with such id.")

        val bookExistsInLibrary = hasBookService
            .isBookInSpecificLibrary( libraryToBorrowFrom.libraryId!!, bookToAdd.bookId!!)
            ?: return ApiResponse(data = null, message = "Error: The book isn't in this library.")

        val oke = borrowsService.isBorrowed(accountToBorrow.accountId!!,bookExistsInLibrary.hasBookId!!)

        if ( oke == null ) {
            val quantity = hasBookService.getQuantity(libraryToBorrowFrom.libraryId!!, bookToAdd.bookId!!)
            if ( quantity <= 0 ) return ApiResponse(data = null, message = "Error: No available copies of this book.")

            hasBookService.subtractQuantityByOne(libraryToBorrowFrom.libraryId!!, bookToAdd.bookId!!)

            val borrows = Borrows (null,accountToBorrow, bookExistsInLibrary, LocalDate.now(),null)
            val saved = borrowsService.save(borrows)
            return ApiResponse(data = saved, message = "OK")
        }
        return ApiResponse(data = null, message = "The book is already borrowed by this user.")
    }

    fun returnBook(data: HasBook,
                   response: HttpServletResponse?, username: String): ApiResponse<String, String> {
        val bookExistsInLibrary = hasBookService
            .isBookInSpecificLibrary( data.library?.libraryId?:-1, data.book?.bookId?:-1)
            ?: return ApiResponse(data = null, message = "Error: The book isn't in this library.")

        val accountToBorrow =accountService.findByEmail(username)
            ?: return ApiResponse(data = null, message = "Error: No account with such username.")
        val borrows = borrowsService.isBorrowed(accountToBorrow.accountId!!,bookExistsInLibrary.hasBookId?:-1)
            ?: return ApiResponse(data = null, message = "Error: This book was already returned.")

        borrowsService.returnBook(borrows.borrowsId!!, LocalDate.of(2020,2,2))
        hasBookService.addQuantityByOne(data.library?.libraryId!!,data.book?.bookId!!)
        return ApiResponse(data = null, message = "OK")
    }


    fun enableAccount(email: String?) {
        return accountService.enableAccount(email!!);
    }
}