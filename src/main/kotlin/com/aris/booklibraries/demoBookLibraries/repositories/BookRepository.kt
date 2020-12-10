package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BookRepository : JpaRepository<Book,Long> {

    //doesnt run without modifying and query
//    @Modifying
//    @Query(value = "delete from Book where title = :title")
    @Transactional
    fun deleteByTitle (title: String)

    //@Query(value = "select * from book where author_id = :authorId", nativeQuery = true)
    fun findByAuthorAuthorId (authorId: Long): List<Book>
}