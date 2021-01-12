package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Borrows
import com.aris.booklibraries.demoBookLibraries.repositories.BorrowsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.time.LocalDate

@Service
class BorrowsService {
    @Autowired
    lateinit var borrowsRepository: BorrowsRepository

    @Transactional
    fun save(entity: Borrows): Borrows? {
        return borrowsRepository.save(entity)
    }

    @Transactional
    fun isBorrowed(userId: Long,hasBookId: Long): Borrows? {
        return borrowsRepository.isBorrowed(userId, hasBookId)
    }

    @Transactional
    fun returnBook(borrowsId: Long, returnDate: LocalDate) {
        borrowsRepository.returnBook(borrowsId, returnDate)
    }

    @Transactional
    fun getBorrowsDetails(userId: Long) : List<String> {
        return borrowsRepository.getBorrowsDetails(userId)
    }

    @Transactional
    fun isBookBorrowed(bookId: Long): BigInteger {
        return borrowsRepository.isBookBorrowed(bookId)
    }

}