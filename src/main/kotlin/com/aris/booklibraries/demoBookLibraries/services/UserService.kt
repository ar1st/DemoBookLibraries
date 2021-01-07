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
    lateinit var userRepository: UserRepository

    @Transactional
    fun findAll(): List<User> {
        return userRepository.findAll() ?: emptyList()
    }

    @Transactional
    fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    @Transactional
    fun findByFirstName(firstName: String): User? {
        return userRepository.findByFirstName(firstName)
    }

    @Transactional
    fun findByEmail(email:String) : User?{
        return userRepository.findByEmail(email)
    }

    @Transactional
    fun logIn(email:String,password: String) : User?{
        return userRepository.logIn(email,password)
    }

    @Transactional
    fun save(entity: User): User? {
        return userRepository.save(entity)
    }

    @Transactional
    fun deleteById(userId: Long) {
        userRepository.deleteById(userId)
    }


}