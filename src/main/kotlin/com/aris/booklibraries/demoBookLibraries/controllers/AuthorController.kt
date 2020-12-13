package com.aris.booklibraries.demoBookLibraries.controllers

import org.springframework.web.bind.annotation.*
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/authors"])
class AuthorController {
    @Autowired
    lateinit var authorService: AuthorService

    @GetMapping("")
    fun getAllAuthors(): List<Author> {
        return authorService.findAll()
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) authorId: Long): Author{
        return authorService.findById(authorId) ?: Author(-1,"rkaj","dwada","dwa")
    }

    @PostMapping("")
    fun createAuthor(request: HttpServletRequest, response: HttpServletResponse,
              @RequestBody data: Author):  Author {
        var createdAuthor =  data
        if ( createdAuthor.email == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            //todo: add error message ("No email found)
        }
        authorService.save(createdAuthor)
        response.status = HttpStatus.ACCEPTED.value()

        return createdAuthor
    }


    // when the author with the given id doesnt exist, it creates him. we want that right?
    @RequestMapping(
            value = ["/{ID}"],
            produces = ["application/json"],
            method = [RequestMethod.PUT])
   // @PutMapping(value = ["{/ID}"])        this doesnt always work. error 505
    fun updateAuthor( response: HttpServletResponse,
                            @RequestBody data: Author, @PathVariable ID: Long):  Author? {
        //val updatedAuthor = authorService.findById(ID)
        data.authorId = ID
        val updatedAuthor = authorService.save(data)
        response.status = HttpStatus.ACCEPTED.value()
        return updatedAuthor
    }

    
}