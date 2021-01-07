package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.services.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(value = ["/bookstest"])
class TestController {

    @Autowired
    lateinit var bookService: BookService

    @GetMapping("")
    fun testZero(): List<Book> {
        return bookService.findAll()
    }

    @GetMapping("/{ID}")
    fun testOne(@PathVariable("ID", required = true) bookId: Long): Book? {
        return bookService.findById(bookId)
    }

    @GetMapping("/{ID}/author")
    fun testTwo(@PathVariable("ID", required = true) bookId: Long): Author? {
        return bookService.findById(bookId)?.author
    }

    @GetMapping("/test/me")
    fun temp(@RequestParam("name", required = false) name: String?): Map<String, String> {
        val actualName = name ?: "UNKNOWN"
        return mapOf("message" to "Hello $actualName")
    }

    @PostMapping("upload/me")
    fun temp2(request: HttpServletRequest, response: HttpServletResponse,
                @RequestBody data: Map<String, String>):  Map<String, String> {
        val mapCopy = mutableMapOf<String,String>()
        mapCopy.putAll(data)

        //val lang = LocaleContextHolder.getLocale().
        val msg = if(LocaleContextHolder.getLocale().language == "el") "apothikeutike" else "added"

        mapCopy["message"] = msg

        response.status = HttpStatus.ACCEPTED.value()

        return mapCopy
    }

}