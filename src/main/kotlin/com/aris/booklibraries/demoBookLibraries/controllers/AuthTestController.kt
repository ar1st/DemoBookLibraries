package com.aris.booklibraries.demoBookLibraries.controllers

import org.springframework.stereotype.Controller
import java.security.Principal

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/principal")
class AuthTestController {

    @GetMapping
    fun retrievePrincipal(principal: Principal?): Principal? {
        return principal
    }
}