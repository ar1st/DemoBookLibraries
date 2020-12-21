package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.HashMap
import javax.servlet.http.HttpServletResponse

@Component
class AuthorExecutor {
    @Autowired
    lateinit var authorService: AuthorService

    fun getAllAuthors(): ApiResponse<List<Author>, String> {
        val allAuthors = authorService.findAll()
        return if ( allAuthors!= null) {
            ApiResponse(data = allAuthors)
        } else {
            ApiResponse(data = null, message = "Sth went wrong")
        }
    }

    fun getAuthorById(authorId: Long,response: HttpServletResponse): ApiResponse<Author,String> {
        val authorToReturn = authorService.findById(authorId)

        return if ( authorToReturn != null ) {
            ApiResponse(data=authorToReturn)
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "No author with such id.")
        }
    }

    fun createAuthor( response: HttpServletResponse, data: Author): ApiResponse<Author,String> {
        var createdAuthor =  data
        if ( createdAuthor.email == null) {
           // response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Insert an email.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = authorService.save(createdAuthor) )
    }

    fun updateAuthor( response: HttpServletResponse,
                      data: Author, ID: Long):  ApiResponse<Author,String> {
        if (ID != data.authorId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Author id does not match Path id.")
        }

        val authorToUpdate = authorService.findById(data.authorId ?: -1)
        if (  authorToUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "No author with such id found.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = authorService.save(data) )
    }

    fun partiallyUpdateAuthor (response: HttpServletResponse,
                               hMap: HashMap<String, Any>, ID: Long) : ApiResponse<Author,String> {
        val authorId =  hMap["authorId"].toString().toLong()

        if ( ID != authorId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Author id does not match Path id.")
        }

        val authorToPartiallyUpdate = authorService.findById(authorId ?: -1)
        if (  authorToPartiallyUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "No author with such id found.")
        }

        authorToPartiallyUpdate.patch(hMap)
        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = authorService.save(authorToPartiallyUpdate))
    }
}