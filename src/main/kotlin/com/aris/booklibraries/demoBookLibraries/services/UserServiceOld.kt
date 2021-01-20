package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.UserOld
import com.aris.booklibraries.demoBookLibraries.repositories.UserRepositoryOld
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Deprecated("should be replaced")
@Service
class UserServiceOld {
    @Autowired
    lateinit var userRepositoryOld: UserRepositoryOld

    @Transactional
    fun findAll(): List<UserOld> {
        return userRepositoryOld.findAll()
    }

    @Transactional
    fun findById(id: Long): UserOld? {
        return userRepositoryOld.findById(id).orElse(null)
    }

    @Transactional
    fun findByFirstName(firstName: String): UserOld? {
        return userRepositoryOld.findByFirstName(firstName)
    }

    @Transactional
    fun findByEmail(email:String) : UserOld?{
        return userRepositoryOld.findByEmail(email)
    }

    @Transactional
    fun logIn(email:String,password: String) : UserOld?{
        return userRepositoryOld.logIn(email,password)
    }

    @Transactional
    fun save(entity: UserOld): UserOld? {
        return userRepositoryOld.save(entity)
    }

    @Transactional
    fun deleteById(userId: Long) {
        userRepositoryOld.deleteById(userId)
    }


}