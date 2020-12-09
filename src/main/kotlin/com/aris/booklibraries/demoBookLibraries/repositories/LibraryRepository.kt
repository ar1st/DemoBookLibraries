package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LibraryRepository : JpaRepository<Library,Long> {
    @Modifying
    @Query(value = "delete  from library inner join city on library.city_id = city.city_id where city.name = :name")
    @Transactional
    fun deleteByCityName(name: String)
}