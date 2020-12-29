package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.repositories.HasBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HasBookService {
    @Autowired
    var hasBookRepository: HasBookRepository? = null

    @Transactional
    fun addBook(libraryId: Long, bookId: Long, quantity: Long) {
        hasBookRepository?.addBookToSpecificLibrary(libraryId, bookId, quantity)
    }

    @Transactional
    fun isBookInSpecificLibrary(libraryId: Long, bookId: Long): HasBook? {
        return hasBookRepository?.isBookInSpecificLibrary(libraryId, bookId)
    }
}