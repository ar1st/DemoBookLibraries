package com.aris.booklibraries.demoBookLibraries.controllers

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

    @GetMapping("")
    fun getAllAuthors(): ApiResponse<List<Author>, String> {
        return ApiResponse(data=authorService.findAll())
    }

    @GetMapping("/{ID}")
    fun getAuthorById (@PathVariable("ID",required = true) authorId: Long): Author {
        return authorService.findById(authorId) ?: Author(-1,"Not Found","not","found")
    }

//    @PostMapping("")
//    fun createAuthor(request: HttpServletRequest, response: HttpServletResponse,
//              @RequestBody data: Author): Author {
//        var createdAuthor =  data
//        if ( createdAuthor.email == null) {
//            response.status = HttpStatus.BAD_REQUEST.value()
//            //todo: add error message ("No email found)
//        }
//        authorService.save(createdAuthor)
//        response.status = HttpStatus.ACCEPTED.value()
//
//        return createdAuthor
//    }


    @RequestMapping(
            value = ["/{ID}"],
            produces = ["application/json"],
            method = [RequestMethod.PUT])
  //  @PutMapping(value = ["{/ID}"],produces = ["application/json"]) not working 405
    fun updateAuthor( response: HttpServletResponse,
                            @RequestBody data: Author, @PathVariable ID: Long):  Author? {
        val authorToUpdate = authorService.findById(data.authorId ?: -1)
        if (ID != data.authorId || authorToUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return null
        }

        val updatedAuthor = authorService.save(data)
        response.status = HttpStatus.ACCEPTED.value()
        return updatedAuthor
    }

    @RequestMapping(
        value = ["/{ID}"],
        produces = ["application/json"],
        method = [RequestMethod.PATCH])
    fun partiallyUpdateAuthor ( response: HttpServletResponse,
                                    @RequestBody hMap: HashMap<String,Any>,@PathVariable ID: Long) : Author? {
        val authorId =  hMap["authorId"].toString().toLong()

        val authorToPartiallyUpdate = authorService.findById(authorId ?: -1)

        if ( ID != authorId || authorToPartiallyUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return null
        }

        authorToPartiallyUpdate.patch(hMap)
        var author = authorService.save(authorToPartiallyUpdate)

        response.status = HttpStatus.ACCEPTED.value()
        return author
    }

}