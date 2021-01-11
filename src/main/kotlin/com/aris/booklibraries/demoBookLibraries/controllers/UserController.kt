package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.executors.UserExecutor
import com.aris.booklibraries.demoBookLibraries.models.BorrowDetails
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.services.BorrowsService
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
    lateinit var borrowsService: BorrowsService
    @Autowired
    lateinit var userExecutor: UserExecutor
    @Autowired
    lateinit var bookExecutor: BookExecutor
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor
    @Autowired
    lateinit var hasBookService: HasBookService

    @GetMapping("/loggedUser/books")
    fun showBorrowedBooks(session: HttpSession, model: Model): String {
        val loggedUser = session.getAttribute("loggedUser")
        val borrow = borrowsService.getBorrowsDetails((loggedUser as User).userId !!)
        val listToReturn = mutableListOf<BorrowDetails>()
        for (element: String in borrow) {
            val parts = element.split(",")
            val details = BorrowDetails(parts[0],parts[1],parts[2],parts[3],parts[4])
            listToReturn.add(details)
        }

        model.addAttribute("borrows", listToReturn)
        return "loggedUser/books"
    }

    @GetMapping("/loggedUser/{userid}/books/{bookid}/libraries/{libraryid}")
    fun borrowBook(@PathVariable("userid",required = true) userId: String,
                   @PathVariable("bookid",required = true) bookId:String,
                   @PathVariable("libraryid",required = true) libraryId:String,
                   model: Model): String {
        val user = userExecutor.getUserById(userId.toLong(),null)
        val book = bookExecutor.getBookById(bookId.toLong(),null)
        val library = libraryExecutor.getLibraryById(libraryId.toLong(),null)

        val response = userExecutor.borrowBook(HasBook(null,library.data!!,book.data!!,0),user.data?.userId!!,null)
        model.addAttribute("message", response.message)
        return "main"
    }

    @GetMapping("/loggedUser/{userId}/return/{hasBookId}")
    fun returnBook(@PathVariable("userId",required = true) userId: String,
                   @PathVariable("hasBookId",required = true) hasBookId: String,
                   model: Model): String{
        val user = userExecutor.getUserById( userId.toLong(),null)
        val hasBook = hasBookService.getById( hasBookId.toLong() )
        val p = userExecutor.returnBook(hasBook!!,null,user.data?.userId!!)
        model.addAttribute("message",p.message)
        return "main"
    }


}