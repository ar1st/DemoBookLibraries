package com.aris.booklibraries.demoBookLibraries.registration.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Repository
interface ConfirmationTokenRepository: JpaRepository<ConfirmationToken, Long> {

    @Transactional
    fun findByToken(token: String): ConfirmationToken?

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    fun updateConfirmedAt(token: String?,
                          confirmedAt: LocalDateTime?): Int
}