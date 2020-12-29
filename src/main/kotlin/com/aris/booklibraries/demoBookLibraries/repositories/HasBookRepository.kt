package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.HasBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface HasBookRepository: JpaRepository<HasBook, Long> {
    @Modifying
    @Query(value="insert into has_book values (:bookId, :libraryId, :quantity)",nativeQuery = true)
    @Transactional
    fun addBookToSpecificLibrary(libraryId: Long, bookId: Long, quantity: Long)

    @Query(value = "select * from has_book where has_book.library_id = :libraryId AND has_book.book_id = :bookId",
        nativeQuery = true)
    @Transactional
    fun isBookInSpecificLibrary(libraryId: Long, bookId: Long): HasBook?
}
