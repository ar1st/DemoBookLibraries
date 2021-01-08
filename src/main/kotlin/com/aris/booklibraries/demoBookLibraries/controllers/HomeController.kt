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
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class HomeController {
    @Autowired
    lateinit var userExecutor: UserExecutor
    @Autowired
    lateinit var bookExecutor: BookExecutor
    @Autowired
    lateinit var borrowsService: BorrowsService
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor
    @Autowired
    lateinit var hasBookService: HasBookService

    @RequestMapping("/")
    fun showMainPage(): String {
        return "main"
    }

    @GetMapping("/users")
    fun showAllUsers(model: Model): String {
        model.addAttribute("users", userExecutor.getAllUsers())
        return "showusers"
    }

    @GetMapping("/books")
    fun showAllBooks(model: Model): String {
        model.addAttribute("books", bookExecutor.getAllBooks())
        return "showbooks"
    }

    @GetMapping("/signup")
    fun signUpUser(@ModelAttribute user: User, model: Model ): String {
        model.addAttribute("user",user)
        return "signup"
    }

    @PostMapping("/signup")
    fun submitForm(@ModelAttribute("user") user: User): String {
        userExecutor.createUser(user, null)
        return "main"
    }

    @GetMapping("/login")
    fun login(@ModelAttribute user: User, model: Model ): String {
        model.addAttribute("user",user)
        return "login"
    }

    @PostMapping("/login")
    fun checkLogin(@ModelAttribute("user") user: User,model: Model,session: HttpSession): String {
        val tryToLog = userExecutor.logIn(user.email!!,user.password!!)
        if (tryToLog.data != null) {
            session.setAttribute("loggedUser", tryToLog.data)
            session.setAttribute("logInError",null )
            return "main"
        }
        session.setAttribute("logInError",tryToLog.message )
        return "login"
    }

    @RequestMapping("/logout")
    fun logout(session: HttpSession): String {
        session.setAttribute("loggedUser", null)
        return "main"
    }

    @GetMapping("/mybooks")
    fun showBorrowedBooks(session: HttpSession, model: Model): String {
        val loggedUser = session.getAttribute("loggedUser")
        val borrow = borrowsService.getBorrowsDetails((loggedUser as User).userId !!)
        val listToReturn = mutableListOf<BorrowDetails>()
        for (element: String in borrow) {
            var parts = element.split(",")
            var details = BorrowDetails(parts[0],parts[1],null,parts[2],parts[3],parts[4])
            listToReturn.add(details)
        }

        model.addAttribute("borrows", listToReturn)
        return "mybooks"
    }

    @GetMapping("/libraries")
    fun showAllLibraries(model: Model,
                         @PathVariable("ID",required = false) libraryId: Long?): String {
        model.addAttribute("libraries", libraryExecutor.getAllLibraries())
        return "showlibraries"

    }

    @GetMapping("/libraries/{ID}")
    fun showBooksFromSpecificLibrary(model: Model,
                                     @PathVariable("ID",required = true) libraryId: Long,
                                    ): String {
        model.addAttribute("books", libraryExecutor.getBooksByLibrary(libraryId,null))
        model.addAttribute("libraryId",libraryId)
        return "showbooksfromspecificlibrary"
    }

    @GetMapping("/borrows/users/{userid}/books/{bookid}/libraries/{libraryid}")
    fun test(@PathVariable("userid",required = true) userId: String,
                @PathVariable("bookid",required = true) bookId:String,
               @PathVariable("libraryid",required = true) libraryId:String,
                model: Model): String {
        val user = userExecutor.getUserById(userId.toLong(),null)
        val book = bookExecutor.getBookById(bookId.toLong(),null)
        val library = libraryExecutor.getLibraryById(libraryId.toLong(),null)

        val p = userExecutor.borrowBook(HasBook(null,library.data!!,book.data!!,0),user.data?.userId!!,null)
        model.addAttribute("message", p.message)
        return "main"
    }

    @GetMapping("/return/{userId}/{hasBookId}")
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