package com.aris.booklibraries.demoBookLibraries.registration.token

import com.aris.booklibraries.demoBookLibraries.models.Account
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class ConfirmationToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val token: String,
        val createdAt: LocalDateTime,
        val expiresAt: LocalDateTime,
        val confirmedAt: LocalDateTime?,
        @ManyToOne
        @JoinColumn(name="account_id  ")
        val account: Account
)
