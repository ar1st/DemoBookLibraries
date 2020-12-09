package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LibraryRepositoy : JpaRepository<Library,Long> {
}