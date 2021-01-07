package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/librariesrest"])
class LibraryController {
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor

    @GetMapping("")
    fun getAllLibraries(): ApiResponse<List<Library>, String> {
        return libraryExecutor.getAllLibraries()
    }

    @GetMapping(value = ["/{ID}"])
    fun getById(@PathVariable("ID",required = true) libraryId: Long,
                response: HttpServletResponse) : ApiResponse<Library, String> {
        return libraryExecutor.getLibraryById(libraryId,response)
    }

    @GetMapping(value=["/{ID}/books"])
    fun getBooksByLibrary(@PathVariable("ID",required = true) libraryId: Long,
                response: HttpServletResponse) : ApiResponse<List<Book>, String> {
        return libraryExecutor.getBooksByLibrary(libraryId,response)
    }

    //todo fix that. error!!!!
    @PostMapping("")
    fun createLibrary(@RequestBody data: Library,
                      response: HttpServletResponse): ApiResponse<Library,String> {
        return libraryExecutor.createLibrary(data, response)
    }

    @PostMapping("/{ID}/books")
    fun addBookToSpecificLibrary(@PathVariable("ID",required = true) libraryId: Long,
                                 @RequestBody data: HasBook, response: HttpServletResponse) : ApiResponse<Book,String> {
        return libraryExecutor.addBookToSpecificLibrary(libraryId, data, response)
    }

    //only changes name. can we change city?
    @PatchMapping("/{ID}")
    fun partiallyUpdateLibrary (response: HttpServletResponse,
                               @RequestBody hMap: HashMap<String, Any>, @PathVariable ID: Long) : ApiResponse<Library,String> {
        return libraryExecutor.partiallyUpdateLibrary(response,hMap,ID)
    }

    @DeleteMapping("/{ID}")
    fun deleteById(@PathVariable("ID", required = true) libraryId: Long,
                   response: HttpServletResponse) : ApiResponse<String,String>{
        return libraryExecutor.deleteById(libraryId,response)
    }

    @DeleteMapping("/deletebook/{ID}")
    fun deleteBookByIdFromAllLibraries(@PathVariable("ID", required = true) bookId: Long,
                                       response: HttpServletResponse): ApiResponse<String, String> {
        return libraryExecutor.deleteBookFromLibraries(bookId, response)
    }

    @DeleteMapping("/{ID}/books")
    fun deleteAllBooksFromSpecificLibrary(@PathVariable("ID", required = true) libraryId: Long,
                                          response: HttpServletResponse) : ApiResponse<String,String>{
        return libraryExecutor.deleteAllBooksFromSpecificLibrary(libraryId,response)
    }

    @DeleteMapping("/{ID}/books/{BOOKID}")
    fun deleteBookFromSpecificLibrary(@PathVariable("ID", required = true) libraryId: Long,
                        @PathVariable(value = "BOOKID", required = true) bookId: Long, response: HttpServletResponse)
                    :  ApiResponse<String,String> {
        return libraryExecutor.deleteBookFromSpecificLibrary(libraryId, bookId, response)
    }
}