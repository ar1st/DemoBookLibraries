package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Librarian
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LibrarianRepository: JpaRepository<Librarian,Long> {

    @Transactional
    @Query(value="SELECT librarian.* " +
            "from librarian inner join account on librarian.account_id = account.account_id " +
            "where account.username = username",nativeQuery = true)
    fun findLibrarianByAccountUsername(username: String): Librarian?
}