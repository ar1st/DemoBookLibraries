package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.repositories.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService {
    @Autowired
    var authorRepository: AuthorRepository? = null

    @Transactional
    fun  save(entity: Author): Author? {
        return authorRepository?.save(entity)
    }

    @Transactional
    fun findById(id: Long): Author? {
        return authorRepository?.findById(id)?.orElse(null)
    }

    @Transactional
    fun findAll(): List<Author> {
        return authorRepository?.findAll() ?: emptyList()
    }

    @Transactional
    fun findByEmail(email: String): Author? {
        return authorRepository?.findByEmail(email)
    }

    @Transactional
    fun deleteById(authorId: Long) {
        authorRepository?.deleteById(authorId)
    }

}