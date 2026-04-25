package com.nainesh.java.mailTest;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // Sender and recipient
        String from = "kishan.auxano@gmail.com";
        String to = "ngoel.main@gmail.com";

        // SMTP server details
        String host = "smtp.gmail.com";
//        String host = "localhost";
        String username = "kishan.auxano@gmail.com";
        String password = "czmfxscwkoiexikh";

        // Set properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject("Test Email from Java");
            message.setText("Hello, this is a test email sent from a Java program!");

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
