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
    @Autowired
    var authorRepository: AuthorRepository? = null


    @Transactional
    fun findAll(): List<Book> {
        return bookRepository?.findAll() ?: emptyList()
    }


    @Transactional
    fun findById(id: Long): Book? {
        return bookRepository?.findById(id)?.orElse(null)
    }

    @Transactional
    fun addBook(entity: Book, author: Author): Book? {

        val matchedAuthor = if ( author.authorId == null) {
            authorRepository?.save(author)
        } else {
            authorRepository?.findById( author.authorId!! )?.orElse(null)
        } ?: return null

        entity.author = matchedAuthor
        return bookRepository?.save(entity)
    }
}