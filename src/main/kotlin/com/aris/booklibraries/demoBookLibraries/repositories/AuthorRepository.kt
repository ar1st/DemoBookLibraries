package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
}