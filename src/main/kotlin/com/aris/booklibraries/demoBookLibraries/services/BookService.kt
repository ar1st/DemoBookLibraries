package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.repositories.AuthorRepository
import com.aris.booklibraries.demoBookLibraries.repositories.BookRepository
import com.aris.booklibraries.demoBookLibraries.repositories.LibraryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService {
    @Autowired
    lateinit var bookRepository: BookRepository
    @Autowired
    lateinit var authorRepository: AuthorRepository
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var libraryRepository: LibraryRepository


    @Transactional
    fun findAll(): List<Book> {
        return bookRepository.findAll()
    }


    @Transactional
    fun findById(id: Long): Book? {
        return bookRepository.findById(id).orElse(null)
    }

    @Transactional
    fun findAllBooksByAuthor(authorId: Long): List<Book> {
        return  bookRepository.findByAuthorAuthorId(authorId)
    }

    @Transactional
    fun findAllBooks(bookId: Long): List<Library> {
        val matchedBook = findById( bookId )

        if ( matchedBook != null) {
            return  libraryRepository.findAllLibraries(bookId)
        }
        return emptyList()
    }

    @Transactional
    fun findAllBooksNotInSpecificLibrary(libraryId: Long): List<Book> {
        return bookRepository.findAllBooksNotInSpecificLibrary(libraryId)
    }

    @Transactional
    fun addBook(entity: Book, author: Author): Book? {

        val matchedAuthor = if ( author.authorId == null) {
            authorRepository.save(author)
        } else {
            authorRepository.findById( author.authorId!! ).orElse(null)

        } ?: return null

        entity.author = matchedAuthor
        return bookRepository.save(entity)
    }

    @Transactional
    fun deleteById(bookId: Long) {
        libraryService.removeBookFromAllLibraries(bookId)
        bookRepository.deleteById(bookId)
    }

    @Transactional
    fun deleteByAuthor(authorId: Long) {
        val booksToDelete = bookRepository.findByAuthorAuthorId(authorId)

        for ( book in booksToDelete) {
            deleteById(book.bookId!!)
        }
    }

    @Transactional
    fun getBookDetails(bookId: Long) : String {
        return bookRepository.getBookDetails(bookId)
    }
}