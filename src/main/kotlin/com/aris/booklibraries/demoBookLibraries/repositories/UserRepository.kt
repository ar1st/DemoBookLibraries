package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface UserRepository :  JpaRepository<User, Long> {
    @Transactional
    fun findByFirstName(firstName: String): User?

    @Transactional
    fun findByEmail(email: String): User?

    @Query(value="select * from user where email = :email AND password = :password", nativeQuery = true)
    @Transactional
    fun logIn(email:String,password:String):User?

    @Transactional
    fun findByFirstNameStartingWith(firstName: String): List<User>
}