package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j // When you annotate a class with @Slf4j, Lombok generates a static Logger field for you.
@Service
@RequiredArgsConstructor
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;

    public void sendSimpleMail(Mail mail) {
        log.info("Starting email preparation...");

        try {
            javaMailSender.send(createSimpleMailMessage(mail));
            log.info("Email has been sent.");
        } catch (MailException me) {
            log.error("Failed to process email sending: {}", me.getMessage(), me);
        }
    }

    SimpleMailMessage createSimpleMailMessage(Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getText());
        Optional.ofNullable(mail.getToCC()).ifPresent(simpleMailMessage::setCc);

        return simpleMailMessage;
    }
}
