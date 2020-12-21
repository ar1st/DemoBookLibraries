package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.middleman.AuthorMiddleMan
import org.springframework.web.bind.annotation.*
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/authors"])
class AuthorController {
    @Autowired
    lateinit var authorService: AuthorService
    @Autowired
    var authorMiddleMan = AuthorMiddleMan()

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
              @RequestBody data: Author): ApiResponse<Author,String> {
        return authorMiddleMan.createAuthor(response,data)
    }


    @RequestMapping(
            value = ["/{ID}"],
            produces = ["application/json"],
            method = [RequestMethod.PUT])
  //  @PutMapping(value = ["{/ID}"],produces = ["application/json"]) not working 405
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