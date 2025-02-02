package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Librarian
import com.aris.booklibraries.demoBookLibraries.repositories.LibrarianRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LibrarianService {
    @Autowired
    lateinit var librarianRepository: LibrarianRepository

    @Transactional
    fun save(entity: Librarian): Librarian? {
        return librarianRepository.save(entity)
    }

    @Transactional
    fun findLibrarianByAccountEmail(email: String): Librarian? {
        return librarianRepository.findLibrarianByAccountEmail(email)
    }
}