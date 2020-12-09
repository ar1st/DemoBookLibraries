package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UserService {
    @Autowired
    var userRepository: UserRepository? = null

    @Transactional
    fun  save(entity: User): User? {
        return userRepository?.save(entity)
    }

    fun findById(id: Long): User? {
        return userRepository?.findById(id)?.orElse(null)
    }

    fun findByFirstName(firstName: String): User? {
        return userRepository?.findByFirstName(firstName)
    }

}