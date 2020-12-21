package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import org.springframework.web.bind.annotation.*
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/authors"])
class AuthorController: BaseController() {
    @Autowired
    lateinit var authorMiddleMan: AuthorExecutor

    @GetMapping("")
    fun getAllAuthors(): ApiResponse<List<Author>, String> {
        return authorMiddleMan.getAllAuthors()
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) authorId: Long,response: HttpServletResponse): ApiResponse<Author,String> {
        return authorMiddleMan.getAuthorById(authorId,response)
    }

    @PostMapping("")
    fun createAuthor(request: HttpServletRequest, response: HttpServletResponse,
              @Valid @RequestBody data: Author): ApiResponse<Author,String> {
        return authorMiddleMan.createAuthor(response,data)
    }


    @RequestMapping(
            value = ["/{ID}"],
            produces = ["application/json"],
            method = [RequestMethod.PUT])
    fun updateAuthor( response: HttpServletResponse,
                            @RequestBody data: Author, @PathVariable ID: Long):  ApiResponse<Author,String> {
        return authorMiddleMan.updateAuthor(response,data,ID)
    }

    @RequestMapping(
        value = ["/{ID}"],
        produces = ["application/json"],
        method = [RequestMethod.PATCH])
    fun partiallyUpdateAuthor ( response: HttpServletResponse,
                                    @RequestBody hMap: HashMap<String,Any>,@PathVariable ID: Long) : ApiResponse<Author,String> {
        return authorMiddleMan.partiallyUpdateAuthor(response,hMap,ID)
    }

}