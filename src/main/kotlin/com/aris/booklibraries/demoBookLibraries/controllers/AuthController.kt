package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.LibrarianExecutor
import com.aris.booklibraries.demoBookLibraries.executors.UserExecutor
import com.aris.booklibraries.demoBookLibraries.models.Librarian
import com.aris.booklibraries.demoBookLibraries.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
class AuthController {
    @Autowired
    lateinit var userExecutor: UserExecutor
    @Autowired
    lateinit var librarianExecutor: LibrarianExecutor

    @GetMapping("/signup")
    fun signUpUser(@ModelAttribute user: User, model: Model): String {
        model.addAttribute("user",user)
        return "auth/signup"
    }

    @PostMapping("/signup")
    fun submitForm(@ModelAttribute("user") user: User): String {
        userExecutor.createUser(user, null)
        return "main"
    }

    @GetMapping("/login")
    fun login(@ModelAttribute user: User?,@ModelAttribute librarian:Librarian?, model: Model): String {
        model.addAttribute("user",user)
        model.addAttribute("librarian",librarian)
        return "auth/login"
    }

    @PostMapping("/loginUser")
    fun checkLoginUser(@ModelAttribute("user") user: User?, model: Model, session: HttpSession): String {
//        while (true){
        val tryToLog = userExecutor.logIn(user?.email!!, user.password!!)
        if (tryToLog.data != null) {
            session.setAttribute("loggedUser", tryToLog.data)
            session.setAttribute("role", "user")
            return "main"
        }
        model.addAttribute("message", tryToLog.message)
        return "redirect:/login"
    //}
    }

    @PostMapping("/loginLibrarian")
    fun checkLoginLibrarian(@ModelAttribute("librarian") librarian: Librarian?, model: Model, session: HttpSession): String {
        val tryToLog = librarianExecutor.logIn(librarian?.email!!,librarian.password!!)
        if (tryToLog.data != null) {
            session.setAttribute("loggedLibrarian", tryToLog.data)
            session.setAttribute("role", "librarian")
            return "main"
        }
        model.addAttribute("message",tryToLog.message )
        return "redirect:/login"
    }

    @RequestMapping("/logout")
    fun logout(session: HttpSession): String {
        session.setAttribute("loggedUser", null)
        session.setAttribute("loggedLibrarian", null)
        session.setAttribute("role", null)
        return "main"
    }
}