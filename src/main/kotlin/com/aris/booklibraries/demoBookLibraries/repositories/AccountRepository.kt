package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface AccountRepository: JpaRepository<Account, Long> {
    @Transactional
    fun findByEmail(email: String): Account?

    @Transactional
    @Modifying
    @Query("UPDATE Account a " +
            "SET a.enabled = 1 WHERE a.email = ?1")
    fun enableAccount(email: String)

}