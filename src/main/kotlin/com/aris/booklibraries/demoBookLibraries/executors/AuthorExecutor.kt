package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import com.aris.booklibraries.demoBookLibraries.services.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.HashMap
import javax.servlet.http.HttpServletResponse

@Component
class AuthorExecutor {
    @Autowired
    lateinit var authorService: AuthorService
    @Autowired
    lateinit var bookService: BookService

    fun getAllAuthors(): ApiResponse<List<Author>, String> {
        val allAuthors = authorService.findAll()
        return if ( allAuthors!= null) {
            ApiResponse(data = allAuthors, message = "OK")
        } else {
            ApiResponse(data = null, message = "Error: Sth went wrong")
        }
    }

    fun getAuthorById(authorId: Long,response: HttpServletResponse): ApiResponse<Author,String> {
        val authorToReturn = authorService.findById(authorId)

        return if ( authorToReturn != null ) {
            ApiResponse(data=authorToReturn, message = "OK")
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No author with such id.")
        }
    }

    fun getAllBooksFromAuthor(authorId: Long,response: HttpServletResponse): ApiResponse<List<Book>,String> {
        val matchedAuthor = authorService.findById(authorId)
        if ( matchedAuthor != null) {
            val booksByAuthor = bookService.findAllBooksByAuthor(matchedAuthor.authorId!!)
            return ApiResponse(data = booksByAuthor, message = "OK")
        }

        response.status = HttpStatus.BAD_REQUEST.value()
        return ApiResponse(data = null, message = "Error: No author with such id.")
    }

    fun createAuthor( response: HttpServletResponse, data: Author): ApiResponse<Author,String> {
        if (data.email == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Insert an email.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = authorService.save(data), message = "OK" )
    }

    fun updateAuthor( response: HttpServletResponse,
                      data: Author, ID: Long):  ApiResponse<Author,String> {
        if (ID != data.authorId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Error: Author id does not match Path id.")
        }

        val authorToUpdate = authorService.findById(data.authorId ?: -1)
        if (  authorToUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data= null, message = "Error: No author with such id found.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = authorService.save(data), message = "OK" )
    }

    fun partiallyUpdateAuthor (response: HttpServletResponse,
                               hMap: HashMap<String, Any>, ID: Long) : ApiResponse<Author,String> {
        val authorId =  hMap["authorId"].toString().toLong()

        if ( ID != authorId ) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Author id does not match Path id.")
        }

        val authorToPartiallyUpdate = authorService.findById(authorId )
        if (  authorToPartiallyUpdate == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "Error: No author with such id found.")
        }

        authorToPartiallyUpdate.patch(hMap)
        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse(data = authorService.save(authorToPartiallyUpdate), message = "OK")
    }

    fun deleteById(authorId: Long, response: HttpServletResponse) : ApiResponse<String,String> {
        val matchedAuthor = authorService.findById(authorId) ?: return ApiResponse(data = null, message = "OK")

        deleteBooksFromAuthor(matchedAuthor.authorId!!, response)
        authorService.deleteById(authorId)
        return ApiResponse( data = null, message = "OK")
    }

    fun deleteBooksFromAuthor(authorId: Long, response: HttpServletResponse) : ApiResponse<String,String> {
        val matchedAuthor = authorService.findById(authorId)

        if ( matchedAuthor == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data = null, message = "Error: No author with such id.")
        }

        bookService.deleteByAuthor(matchedAuthor.authorId!!)
        return ApiResponse(data = null, message = "OK")
    }
}