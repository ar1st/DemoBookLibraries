package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.Borrows
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
interface BorrowsRepository: JpaRepository<Borrows, Long> {
    @Query(value="SELECT * from borrows " +
            "where user_id = :userId and has_book_id = :hasBookId and returned_date is null",
                nativeQuery = true)
    @Transactional
    fun isBorrowed(userId: Long,hasBookId: Long): Borrows?

    @Modifying
    @Query(value = "update borrows set returned_date = :date where borrows_id= :borrowsId ", nativeQuery = true)
    @Transactional
    fun returnBook(borrowsId: Long, date: LocalDate)

//    @Query(value = "select * from borrows where user_id = :userId", nativeQuery = true)
//    @Transactional
//    fun getBorrowsDetails(userId: Long): List<Borrows>

    @Query(value = "select book.title,library.name,borrows.borrowing_date,borrows.returned_date" +
            " from borrows inner join has_book on borrows.has_book_id = has_book.has_book_id " +
            "inner join book on book.book_id = has_book.book_id " +
            "inner join library on library.library_id = has_book.library_id" +
            " where borrows.user_id =  :userId", nativeQuery = true)
    @Transactional
    fun getBorrowsDetails(userId: Long): List<String>
}