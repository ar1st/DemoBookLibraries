package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.repositories.AuthorRepository
import com.aris.booklibraries.demoBookLibraries.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService {
    @Autowired
    var bookRepository: BookRepository? = null

    @Transactional
    fun  save(entity: Book): Book? {
        return bookRepository?.save(entity)
    }
}