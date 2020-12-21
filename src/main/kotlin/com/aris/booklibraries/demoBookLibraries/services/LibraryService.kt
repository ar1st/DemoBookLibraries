package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.City
import com.aris.booklibraries.demoBookLibraries.models.Library
import com.aris.booklibraries.demoBookLibraries.repositories.BookRepository
import com.aris.booklibraries.demoBookLibraries.repositories.CityRepository
import com.aris.booklibraries.demoBookLibraries.repositories.LibraryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LibraryService {
    @Autowired
    var libraryRepository: LibraryRepository? = null
    @Autowired
    var cityRepository: CityRepository? = null
    @Autowired
    var bookRepository: BookRepository? = null

    @Transactional
    fun findAll(): List<Library> {
        return libraryRepository?.findAll()?: emptyList()
    }

    @Transactional
    fun findById(libraryId : Long) : Library? {
        return libraryRepository?.findById(libraryId)?.orElse(null)
    }

    @Transactional
    fun addLibrary(entity: Library, city: City): Library? {
        val matchedCity = if ( city.cityId == null) {
            cityRepository?.save(city)
        } else {
            cityRepository?.findById( city.cityId!! )?.orElse(null)
        } ?: return null

        entity.city = matchedCity
        return libraryRepository?.save(entity)
    }

    @Transactional
    fun addBook(library: Library, book: Book) {
        val matchedBook = if ( book.bookId == null) {
            bookRepository?.save(book)
        } else {
            bookRepository?.findById(book.bookId!!)?.orElse(null)
        }

        val matchedLibrary = libraryRepository?.findById(library.libraryId!!)?.orElse(null)

        matchedLibrary?.books?.add(element = matchedBook!! )
    }

//    @Transactional
//    fun findAllBooks(library: Library): List<Book> {
//        val matchedLibrary = libraryRepository?.findById(library.libraryId!!)?.orElse(null)
//
//        if ( matchedLibrary != null) {
//            println("hereeeeeeeee  no books")
//            val books =  libraryRepository?.findAllBooks(matchedLibrary.libraryId!!)
//            println("hereeeeeeeee  $books")
//            return books as List<Book>
//        } else {
//            return emptyList()
//        }
//    }
        @Transactional
        fun findAllBooks(library: Library): List<Book> {
            val matchedLibrary = findById( library.libraryId!! )

            if ( matchedLibrary != null) {
                return libraryRepository?.findAllBooks(matchedLibrary.libraryId!!) ?: emptyList()
            }

            return emptyList()
        }
}