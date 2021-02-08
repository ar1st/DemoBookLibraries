package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AccountExecutor
import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.repositories.AccountRepository
import com.aris.booklibraries.demoBookLibraries.services.HasBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpSession

@Controller
class UserController {

    @Autowired
    lateinit var accountExecutor: AccountExecutor
    @Autowired
    lateinit var bookExecutor: BookExecutor
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor
    @Autowired
    lateinit var hasBookService: HasBookService
    @Autowired
    lateinit var accountRepository: AccountRepository

    @GetMapping("/loggedUser/{username}/books")
    fun showBorrowedBooks(@PathVariable("username",required = true) username: String,
                          session: HttpSession?, model: Model): String {
        val listToReturn = bookExecutor.findBorrowedBooksByUser(username)

        if ( listToReturn.size == 0)
            model.addAttribute("borrows", null)
        else
            model.addAttribute("borrows", listToReturn)

        return "homepage/homepage-user.html"
    }

    @GetMapping("/loggedUser/{username}/books/{bookid}/libraries/{libraryid}")
    fun borrowBook(@PathVariable("username",required = true) username: String,
                   @PathVariable("bookid",required = true) bookId:String,
                   @PathVariable("libraryid",required = true) libraryId:String,
                   model: Model): String {
        val account = accountExecutor.findByUsername(username)
        val book = bookExecutor.getBookById(bookId.toLong(),null)
        val library = libraryExecutor.getLibraryById(libraryId.toLong(),null)

        val response = accountExecutor.borrowBook(HasBook(null,library.data!!,book.data!!,0),account.data?.email!!,null)
        model.addAttribute("message", response.message)
        val listToReturn = bookExecutor.findBorrowedBooksByUser(username)
        model.addAttribute("borrows",listToReturn)

        return "homepage/homepage-user.html"
    }

    @GetMapping("/loggedUser/{username}/return/{hasBookId}")
    fun returnBook(@PathVariable("username",required = true) username: String,
                   @PathVariable("hasBookId",required = true) hasBookId: String,
                   model: Model): String{
        val account = accountRepository.findByEmail(username)
        val hasBook = hasBookService.getById( hasBookId.toLong() )
        val response = accountExecutor.returnBook(hasBook!!,null,account?.email!!)
        model.addAttribute("message",response.message)

        val listToReturn = bookExecutor.findBorrowedBooksByUser(username)
        model.addAttribute("borrows",if (listToReturn.size == 0) null else listToReturn)

        return "homepage/homepage-user.html"
    }
}