package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.repositories.HasBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HasBookService {
    @Autowired
    lateinit var hasBookRepository: HasBookRepository

    @Transactional
    fun getById(hasBookId: Long): HasBook? {
        return hasBookRepository.findById(hasBookId).orElse(null)
    }

    @Transactional
    fun addBook(libraryId: Long, bookId: Long, quantity: Long) {
        hasBookRepository.addBookToSpecificLibrary(libraryId, bookId, quantity)
    }

    @Transactional
    fun isBookInSpecificLibrary(libraryId: Long, bookId: Long): HasBook? {
        return hasBookRepository.isBookInSpecificLibrary(libraryId, bookId)
    }

    @Transactional
    fun getQuantity(libraryId: Long, bookId: Long): Int {
        return hasBookRepository.getQuantity(libraryId, bookId) ?: 0
    }

    @Transactional
    fun subtractQuantityByOne(libraryId: Long, bookId: Long){
        hasBookRepository.subtractQuantityByOne(libraryId,bookId)
    }

    @Transactional
    fun addQuantityByOne(libraryId: Long, bookId: Long){
        hasBookRepository.addQuantityByOne(libraryId,bookId)
    }
}