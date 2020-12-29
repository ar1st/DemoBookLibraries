package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.HasBook
import com.aris.booklibraries.demoBookLibraries.models.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LibraryRepository : JpaRepository<Library,Long> {
    @Transactional
    fun findByCityCityId(cityId: Long): List<Library>

    @Query(value="SELECT library.* " +
            "FROM library inner join has_book  " +
            "on library.library_id = has_book.library_id " +
            "inner join book on book.book_id=has_book.book_id " +
            "where book.book_id = :bookId"
        ,nativeQuery=true)
    @Transactional
    fun findAllLibraries(bookId: Long): List<Library>

    @Transactional
    fun deleteByCityCityId(cityId: Long)

    @Modifying
    @Query(value="delete l from library l inner join city c on l.city_id = c.city_id where c.name = :cityName",nativeQuery = true)
    @Transactional
    fun deleteByCityName(cityName: String)

    @Modifying
    @Query(value = "delete from has_book where has_book.library_id = :libraryId", nativeQuery = true)
    @Transactional
    fun deleteAllBooksFromSpecificLibrary(libraryId: Long)

    @Modifying
    @Query(value = "delete from has_book where has_book.book_id = :bookId", nativeQuery = true)
    @Transactional
    fun removeBookFromAllLibraries(bookId: Long)

    @Modifying
    @Query(value = " delete from has_book where has_book.book_id = :bookId AND has_book.library_id = :libraryId",
        nativeQuery = true)
    @Transactional
    fun removeBookFromSpecificLibrary(libraryId: Long, bookId: Long)
}