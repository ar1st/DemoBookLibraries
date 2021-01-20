package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BookRepository : JpaRepository<Book,Long> {

    @Transactional
    fun deleteByTitle (title: String)

    fun findByAuthorAuthorId (authorId: Long): List<Book>

    @Transactional
    fun deleteByAuthorAuthorId (authorId: Long)

    @Query(value="SELECT book.* " +
            "FROM library inner join has_book  " +
            "on library.library_id = has_book.library_id " +
            "inner join book on book.book_id=has_book.book_id " +
            "where library.library_id = :libraryId"
        ,nativeQuery=true)
    @Transactional
    fun findAllBooks(libraryId: Long): List<Book>

    @Query(value="select b2.* from book b2  " +
            "where b2.book_id not in(  " +
            "SELECT book.book_id FROM book inner join has_book on book.book_id = has_book.book_id " +
            "inner join library on library.library_id = has_book.library_id " +
            "where library.library_id = :libraryId) "
        ,nativeQuery=true)
    @Transactional
    fun findAllBooksNotInSpecificLibrary(libraryId: Long): List<Book>
}