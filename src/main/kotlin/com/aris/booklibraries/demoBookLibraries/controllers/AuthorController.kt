package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.AuthorExecutor
import org.springframework.web.bind.annotation.*
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/authors"])
class AuthorController{
    @Autowired
    lateinit var authorExecutor: AuthorExecutor

    @GetMapping("")
    fun getAllAuthors(): ApiResponse<List<Author>, String> {
        return authorExecutor.getAllAuthors()
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) authorId: Long,response: HttpServletResponse): ApiResponse<Author,String> {
        return authorExecutor.getAuthorById(authorId,response)
    }

    @GetMapping("/{ID}/books")
    fun getAllBooksFromAuthor(@PathVariable("ID",required = true) authorId: Long,
                              response: HttpServletResponse): ApiResponse<List<Book>,String>{
        return authorExecutor.getAllBooksFromAuthor(authorId, response)
    }

    @PostMapping("")
    fun createAuthor(@Valid @RequestBody data: Author,
                     response: HttpServletResponse): ApiResponse<Author,String> {
        return authorExecutor.createAuthor(response,data)
    }

    @RequestMapping(
            value = ["/{ID}"],
            produces = ["application/json"],
            method = [RequestMethod.PUT])
    fun updateAuthor( response: HttpServletResponse,
                            @RequestBody data: Author, @PathVariable ID: Long):  ApiResponse<Author,String> {
        return authorExecutor.updateAuthor(response,data,ID)
    }

    @RequestMapping(
        value = ["/{ID}"],
        produces = ["application/json"],
        method = [RequestMethod.PATCH])
    fun partiallyUpdateAuthor ( response: HttpServletResponse,
                                    @RequestBody hMap: HashMap<String,Any>,@PathVariable ID: Long) : ApiResponse<Author,String> {
        return authorExecutor.partiallyUpdateAuthor(response,hMap,ID)
    }

    @DeleteMapping("/{ID}/books")
    fun deleteBooksByAuthor(@PathVariable("ID",required = true) authorId: Long,
                            response: HttpServletResponse): ApiResponse<String,String>{
        return authorExecutor.deleteBooksFromAuthor(authorId,response)
    }

    @DeleteMapping("/{ID}")
    fun deleteById(@PathVariable("ID",required = true) authorId: Long,
                            response: HttpServletResponse): ApiResponse<String,String>{
        return authorExecutor.deleteById(authorId,response)
    }
}