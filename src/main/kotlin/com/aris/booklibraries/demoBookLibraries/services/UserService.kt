package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Address
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.repositories.AddressRepository
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
    fun findAll(): List<User> {
        return userRepository?.findAll() ?: emptyList()
    }

    @Transactional
    fun findById(id: Long): User? {
        return userRepository?.findById(id)?.orElse(null)
    }

    @Transactional
    fun findByFirstName(firstName: String): User? {
        return userRepository?.findByFirstName(firstName)
    }

    @Transactional
    fun save(entity: User): User? {
        return userRepository?.save(entity)
    }

    @Transactional
    fun deleteById(userId: Long) {
        userRepository?.deleteById(userId)
    }


}