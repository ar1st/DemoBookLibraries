package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository :  JpaRepository<User, Long> {
    fun findByFirstName(firstName: String): User?;
}