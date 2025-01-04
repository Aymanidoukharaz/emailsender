package com.genuinecoder.java_mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {
    private final JavaMailSenderImpl mailSender;

    public EmailController(JavaMailSender javaMailSender, JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }


    @RequestMapping("/send-email-with-attachement")
    public String sendEmailWithAttachement() {
        try {


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("bibliothequeprojet31@gmail.com");
            helper.setTo("bibliothequeprojet31@gmail.com");
            helper.setSubject("Java email with attachment");
            helper.setText("Please find the attached file. bellow");

            helper.addAttachment("logo.jpg" , new File("C:\\ME\\project\\emailsender\\logo.jpg"));
            helper.addAttachment("assurance.pdf" , new File("C:\\ME\\project\\emailsender\\assurance.pdf"));

            mailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            return e.getMessage();
        }

    }



    @RequestMapping("/emailhtml")
    public String sendHtmlEmail() {
        try {


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("bibliothequeprojet31@gmail.com");
            helper.setTo("aymanidoukharaz@gmail.com");
            helper.setSubject("Java email with attachment");

            try(var inputStream  = EmailController.class.getResourceAsStream("/templates/email.html")){
            helper.setText(
                    new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),true
            );}

            helper.addInline("logo1.jpg" , new File("C:\\ME\\project\\emailsender\\logo1.jpg"));

            mailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}
