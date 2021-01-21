package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AccountExecutor
import com.aris.booklibraries.demoBookLibraries.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
class AuthController {
    @Autowired
    lateinit var accountExecutor: AccountExecutor

    @GetMapping("/signup")
    fun signUpUser(@ModelAttribute registrationDetails: RegistrationDetails, model: Model): String {
        model.addAttribute("registrationDetails",registrationDetails)
        return "auth/signup"
    }

    @PostMapping("/signup")
    fun submitForm(@ModelAttribute("registrationDetails") registrationDetails: RegistrationDetails,
                    model:Model,response: HttpServletResponse): String {

        val response = accountExecutor.createAccountAndUser(registrationDetails,response)

        if ( response.data == null) {
            model.addAttribute("error",response.message)
            return "auth/signup.html"
        }
        model.addAttribute("message",response.message)
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
        val role =  SecurityContextHolder.getContext().authentication.authorities.toString()
        if ( role == "[USER]")
            return "homepage/homepage-user"
        else if ( role == "[LIBRARIAN]")
            return "homepage/homepage-librarian"

        return "main.html"
    }

}