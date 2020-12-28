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
    @Autowired
    var bookService: BookService? = null

    @Transactional
    fun findAll(): List<Library> {
        return libraryRepository?.findAll()?: emptyList()
    }

    @Transactional
    fun findAllLibrariesByCity(cityId: Long): List<Library> {
        return  libraryRepository?.findByCityCityId(cityId) ?: emptyList()
    }

    @Transactional
    fun findById(libraryId : Long) : Library? {
        return libraryRepository?.findById(libraryId)?.orElse(null)
    }

    @Transactional
    fun save(libraryToPartiallyUpdate: Library): Library? {
        return libraryRepository?.save(libraryToPartiallyUpdate)
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
            val bookToCreate = Book(null,book.title, null)
            bookService?.addBook(bookToCreate, book.author!!)
        } else {
            bookRepository?.findById(book.bookId!!)?.orElse(null)
        }

        val matchedLibrary = libraryRepository?.findById(library.libraryId!!)?.orElse(null)

        matchedLibrary?.books?.   add(element = matchedBook!! )
    }

    @Transactional
    fun findAllBooks(libraryId: Long): List<Book> {
        val matchedLibrary = findById( libraryId )

        if ( matchedLibrary != null) {
            return  bookRepository?.findAllBooks(matchedLibrary.libraryId!!) ?: emptyList()
        }
        return emptyList()
    }

    @Transactional
    fun deleteById(libraryId: Long) {
        libraryRepository?.deleteById(libraryId)
    }

    @Transactional
    fun deleteAllBooksFromSpecificLibrary(libraryId: Long) {
        val matchedLibrary = findById( libraryId)
        if (matchedLibrary != null ) {
            libraryRepository?.deleteAllBooksFromSpecificLibrary(matchedLibrary.libraryId!!)
        }
    }

    @Transactional
    fun removeBookFromAllLibraries(bookId: Long) {
        libraryRepository?.removeBookFromAllLibraries(bookId)
    }

    @Transactional
    fun removeBookFromSpecificLibrary(libraryId: Long, bookId: Long) {
        libraryRepository?.removeBookFromSpecificLibrary(libraryId,bookId)
    }

    @Transactional
    fun deleteByCity(cityId: Long) {
        val librariesToDelete = libraryRepository?.findByCityCityId(cityId) ?: emptyList()

        for ( library in librariesToDelete) {
            libraryRepository?.deleteAllBooksFromSpecificLibrary(library.libraryId!!)
            deleteById(library.libraryId!!)
        }
    }


}