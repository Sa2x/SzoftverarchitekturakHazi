package com.example.backend.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailService {
    @Autowired
    private val emailSender: JavaMailSender? = null

    fun sendSimpleMessage(
        to: String?, subject: String?, text: String?
    ) = with(emailSender) {
            val message = SimpleMailMessage()
            message.setFrom("noreply@rezipe.com")
            message.setTo(to)
            if (subject != null) {
                message.setSubject(subject)
            }
            if (text != null) {
                message.setText(text)
            }
            this?.send(message)
        }
}