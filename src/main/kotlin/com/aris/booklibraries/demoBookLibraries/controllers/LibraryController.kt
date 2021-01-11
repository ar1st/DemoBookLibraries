package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.executors.LibraryExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class LibraryController {
    @Autowired
    lateinit var libraryExecutor: LibraryExecutor

    @GetMapping("/libraries/librariesToBorrowFrom")
    fun showAllLibrariesToBorrowFrom(model: Model): String {
        model.addAttribute("libraries", libraryExecutor.getAllLibraries())
        return "libraries/librariesToBorrowFrom"
    }

    @GetMapping("/libraries/{ID}")
    fun showBooksFromSpecificLibrary(@PathVariable("ID", required = true) libraryId: Long,
                                     model: Model
    ): String {
        model.addAttribute("books", libraryExecutor.getBooksByLibrary(libraryId,null))
        model.addAttribute("libraryId",libraryId)
        return "libraries/library/books"
    }
}