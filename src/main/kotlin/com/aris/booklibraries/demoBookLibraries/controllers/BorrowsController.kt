package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.BookExecutor
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/borrows")
class BorrowsController {
    @Autowired
    lateinit var borrowsExecutor: BookExecutor

    @GetMapping("")
    fun getAllAuthors(): ApiResponse<String, String> {
        return ApiResponse("success","oke")
    }

}