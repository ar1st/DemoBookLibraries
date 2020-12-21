package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger

@Repository
interface LibraryRepository : JpaRepository<Library,Long> {
//    @Modifying
//    @Query(value = "delete from Library where city_id = :cityId")
    @Transactional
    fun deleteByCityCityId(cityId: Long)

    @Modifying
    @Query(value="delete l from library l inner join city c on l.city_id = c.city_id where c.name = :cityName",nativeQuery = true)
    @Transactional
    fun deleteByCityName(cityName: String)

    @Query(value="SELECT book.* FROM library  inner join has_books  on library.library_id= has_books.library_id inner join book  on book.book_id=has_books.book_id where library.library_id = :libraryId"
            ,nativeQuery=true)
    @Transactional
    fun findAllBooks(libraryId: Long): List<Book>
}