package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AccountExecutor
import com.aris.booklibraries.demoBookLibraries.executors.AuthorityExecutor
import com.aris.booklibraries.demoBookLibraries.executors.UserExecutor
import com.aris.booklibraries.demoBookLibraries.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class AuthController {

    @Autowired
    lateinit var accountExecutor: AccountExecutor
    @Autowired
    lateinit var userExecutor: UserExecutor
    @Autowired
    lateinit var authorityExecutor: AuthorityExecutor


    @GetMapping("/signup")
    fun signUpUser(@ModelAttribute registrationDetails: RegistrationDetails, model: Model): String {
        model.addAttribute("registrationDetails",registrationDetails)
        return "auth/signup"
    }

    @PostMapping("/signup")
    fun submitForm(@ModelAttribute("registrationDetails") registrationDetails: RegistrationDetails,
                    model:Model,response: HttpServletResponse): String {

        if ( registrationDetails.username.isNullOrEmpty() || registrationDetails.password.isNullOrEmpty()
            ||registrationDetails.firstName.isNullOrEmpty() ||registrationDetails.lastName.isNullOrEmpty() ){
            model.addAttribute("error","Fill all fields.")
            return "auth/signup"
        }

        val encoder = BCryptPasswordEncoder(10)
        val encodedPass = encoder.encode(registrationDetails.password)
        val accountToCreate = Account( null,registrationDetails.username,encodedPass,1)
        val createdAccount = accountExecutor.createAccount(accountToCreate,response)

        if ( createdAccount.data == null) {
            model.addAttribute("error",createdAccount.message)
            return "auth/signup"
        }

        val authorityToCreate = Authority(null,createdAccount.data, Role.USER.value)
        authorityExecutor.createAuthority(authorityToCreate,response)

        val userToCreate = User(null,registrationDetails.firstName,registrationDetails.lastName,createdAccount.data)
        val createdUser = userExecutor.createUser(userToCreate,response)
        if ( createdUser.data == null) {
            model.addAttribute("error",createdUser.message)
            return "auth/signup"
        }

        model.addAttribute("message","You can login now :)")
        return "auth/login.html"
    }

    // Index
    @RequestMapping("/")
    fun index(): String? {
        return main()
    }

    // Login form
    @RequestMapping("login.html")
    fun login(model: Model, error:String?): String? {

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.")

        return "auth/login.html"
    }

    // Main form
    @RequestMapping("/main.html")
    fun main(): String? {
        val p = SecurityContextHolder.getContext().authentication.principal.javaClass
        val n = SecurityContextHolder.getContext().authentication.credentials
        val l = SecurityContextHolder.getContext().authentication.authorities
        val m = SecurityContextHolder.getContext().authentication.details
        val t = SecurityContextHolder.getContext().authentication.name
        return "main.html"
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    fun loginError(model: Model): String? {
        model.addAttribute("loginError", true)
        return "auth/login.html"
    }

//    @GetMapping("/login")
//    fun login(model: Model): String {
//        println()
//        //model.addAttribute("user",user)
//        return "auth/login"
//    }
//
//
//
//    @PostMapping("/login")
//    fun checkLoginUser(@ModelAttribute("user") user: User, model: Model, session: HttpSession): String {
//        val tryToLog = userExecutor.logIn(user.email!!, user.password!!)
//        if (tryToLog.data != null) {
//            session.setAttribute("loggedUser", tryToLog.data)
//            session.setAttribute("role", "user")
//            return "main"
//        }
//        model.addAttribute("message", tryToLog.message)
//        return "redirect:/login"
//    }

//    //todo delete this
//    @PostMapping("/loginLibrarian")
//    fun checkLoginLibrarian(@ModelAttribute("librarian") librarian: Librarian?, model: Model, session: HttpSession): String {
//        val tryToLog = librarianExecutor.logIn(librarian?.email!!,librarian.password!!)
//        if (tryToLog.data != null) {
//            session.setAttribute("loggedLibrarian", tryToLog.data)
//            session.setAttribute("role", "librarian")
//            return "main"
//        }
//        model.addAttribute("message",tryToLog.message )
//        return "redirect:/login"
//    }

    @RequestMapping("/logout")
    fun logout(session: HttpSession): String {
        session.setAttribute("loggedUser", null)
        session.setAttribute("loggedLibrarian", null)
        session.setAttribute("role", null)
        return "main.html"
    }
}