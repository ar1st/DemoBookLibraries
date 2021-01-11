package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Librarian
import com.aris.booklibraries.demoBookLibraries.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LibrarianRepository: JpaRepository<Librarian,Long> {
    @Query(value="select * from librarian where email = :email AND password = :password", nativeQuery = true)
    @Transactional
    fun logIn(email:String,password:String): Librarian?
}