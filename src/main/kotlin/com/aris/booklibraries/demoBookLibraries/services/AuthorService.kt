package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.repositories.AuthorRepository
import com.aris.booklibraries.demoBookLibraries.repositories.UserRepository
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

}