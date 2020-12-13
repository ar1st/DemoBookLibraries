package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {

    @Transactional
    fun deleteByFirstName(name: String)

    @Transactional
    fun findByEmail(email:String): Author?
}