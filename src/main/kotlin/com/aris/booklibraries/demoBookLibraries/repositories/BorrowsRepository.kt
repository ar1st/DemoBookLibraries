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
}