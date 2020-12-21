package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LibraryRepository : JpaRepository<Library,Long> {
    @Transactional
    fun deleteByCityCityId(cityId: Long)

    @Modifying
    @Query(value="delete l from library l inner join city c on l.city_id = c.city_id where c.name = :cityName",nativeQuery = true)
    @Transactional
    fun deleteByCityName(cityName: String)

    @Modifying
    @Query(value = "delete from has_books where has_books.library_id = :libraryId", nativeQuery = true)
    @Transactional
    fun deleteAllBooks(libraryId: Long)

    @Modifying
    @Query(value = "delete from has_books where has_books.book_id = :bookId", nativeQuery = true)
    @Transactional
    fun deleteBookById(bookId: Long)
}