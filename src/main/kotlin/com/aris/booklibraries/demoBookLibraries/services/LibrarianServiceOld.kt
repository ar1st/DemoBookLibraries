package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.LibrarianOld
import com.aris.booklibraries.demoBookLibraries.repositories.LibrarianRepositoryOld
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Deprecated("Should be replaced")
@Service
class LibrarianServiceOld {
    @Autowired
    lateinit var librarianRepositoryOld: LibrarianRepositoryOld

    @Transactional
    fun save(entity: LibrarianOld): LibrarianOld?{
        return librarianRepositoryOld.save(entity)
    }

    @Transactional
    fun logIn(email:String,password: String) : LibrarianOld?{
        return librarianRepositoryOld.logIn(email,password)
    }
}