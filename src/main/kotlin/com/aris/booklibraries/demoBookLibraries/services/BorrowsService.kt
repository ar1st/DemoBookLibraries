//package com.aris.booklibraries.demoBookLibraries.services
//
//import com.aris.booklibraries.demoBookLibraries.models.Borrows
//import com.aris.booklibraries.demoBookLibraries.repositories.BookRepository
//import com.aris.booklibraries.demoBookLibraries.repositories.BorrowsRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//class BorrowsService {
//    @Autowired
//    lateinit var borrowsRepository: BorrowsRepository
//
//    @Transactional
//    fun save(entity: Borrows) {
//
//        borrowsRepository.save(entity)
//    }
//}