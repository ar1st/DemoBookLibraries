package com.aris.booklibraries.demoBookLibraries.newuser

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface NewUserRepository: JpaRepository<Account,Long> {
    @Transactional
    fun findByUsername(username:String): Account?
}