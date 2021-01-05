package com.aris.booklibraries.demoBookLibraries.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HelloWorldController {

    @GetMapping("/hello")
    fun hello(model: Model): String {
        model.addAttribute("message", "Hello World")
        return "helloworld"
    }

    @GetMapping("/style")
    fun style(): String {
        return "add-css-js-demo"
    }



}