//package com.aris.booklibraries.demoBookLibraries.registration.email
//
//import org.slf4j.LoggerFactory
//import org.springframework.mail.javamail.JavaMailSender
//import org.springframework.mail.javamail.MimeMessageHelper
//import org.springframework.scheduling.annotation.Async
//import org.springframework.stereotype.Service
//import javax.mail.MessagingException
//
//@Service
//class EmailServiceTest: EmailSender {
//    private val LOGGER = LoggerFactory.getLogger(EmailService::class.java)
//
//    private val mailSender: JavaMailSender? = null
//
//    @Async
//    override fun send(to: String, email: String) {
//        try {
//           val mimeMessage = mailSender.createMimeMessage()
//            val helper = MimeMessageHelper(mimeMessage, "utf-8")
//            helper.setText(email, true)
//            helper.setTo(to)
//            helper.setSubject("Confirm your email")
//            helper.setFrom("aristnm@hotmail.com")
//            mailSender?.send(mimeMessage)
//
//        } catch (e: MessagingException) {
//            LOGGER.error("failed to send email", e)
//        }
//
//    }
//}