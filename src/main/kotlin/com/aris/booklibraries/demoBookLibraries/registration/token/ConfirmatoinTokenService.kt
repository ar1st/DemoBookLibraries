package com.aris.booklibraries.demoBookLibraries.registration.token

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime




@Service
class ConfirmationTokenService {
    @Autowired
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    fun save(entity: ConfirmationToken) {
        confirmationTokenRepository.save(entity)
    }

    fun getToken(token: String): ConfirmationToken? {
        return confirmationTokenRepository.findByToken(token)
    }

    fun setConfirmedAt(token: String?): Int {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now())
    }
}