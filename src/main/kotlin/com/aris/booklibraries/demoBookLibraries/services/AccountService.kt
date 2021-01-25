package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Account
import com.aris.booklibraries.demoBookLibraries.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Transactional
    fun save(entity: Account): Account? {
        return accountRepository.save(entity)
    }

    @Transactional
    fun findByEmail(email:String): Account? {
        return accountRepository.findByEmail(email)
    }

}