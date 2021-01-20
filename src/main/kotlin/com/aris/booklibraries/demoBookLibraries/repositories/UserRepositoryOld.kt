package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.UserOld
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Deprecated("should be replaced")
@Repository
interface UserRepositoryOld :  JpaRepository<UserOld, Long> {
    @Transactional
    fun findByFirstName(firstName: String): UserOld?

    @Transactional
    fun findByEmail(email: String): UserOld?

    @Query(value="select * from user where email = :email AND password = :password", nativeQuery = true)
    @Transactional
    fun logIn(email:String,password:String):UserOld?

    @Transactional
    fun findByFirstNameStartingWith(firstName: String): List<UserOld>
}