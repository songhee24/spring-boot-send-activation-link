package com.example.springbootsendactivationlink.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/*note
   This service class is required to send the activation or confirmation
   link to the user’s email address once they completed the registration.
   To achieve this functionality we are using the Spring Mail API.
   As you know we have already added the email related properties in the application.properties file,
   so Email Service class we need to define the method to send the email.

*/

@Service("emailService")
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }
}
