package com.aris.booklibraries.demoBookLibraries.registration.email;

import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class EmailService implements EmailSender {

    //alternative send method can be found at
    //https://github.com/amigoscode/login-registration-backend/blob/master/src/main/java/com/example/demo/email/EmailService.java

    @Override
    @Async
    public  void send(@NotNull String to, @NotNull String email) {
        // Set required configs
        String from = "demolibraryapp@hotmail.com";
        String host = "smtp.live.com";
        String port = "25";
        String user = "demolibraryapp@hotmail.com";
        String password = "msensispr0ject";

        // Set system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.user", user);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set from email address
            message.setFrom(new InternetAddress(from, "Library App"));
            // Set the recipient email address
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            // Set email subject
            message.setSubject("Confirm your email.");
            // Set email body
            message.setText(email,"utf-8", "html");
            // Set configs for sending email
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            // Send email
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
