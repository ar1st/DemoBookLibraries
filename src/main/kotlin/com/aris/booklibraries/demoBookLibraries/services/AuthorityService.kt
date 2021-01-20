package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Account
import com.aris.booklibraries.demoBookLibraries.models.Authority
import com.aris.booklibraries.demoBookLibraries.repositories.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorityService {
    @Autowired
    lateinit var authorityRepository: AuthorityRepository

    @Transactional
    fun save(entity: Authority): Authority? {
        return authorityRepository.save(entity)
    }
}