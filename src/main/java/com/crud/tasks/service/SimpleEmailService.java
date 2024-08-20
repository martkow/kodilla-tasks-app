package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j // When you annotate a class with @Slf4j, Lombok generates a static Logger field for you.
@Service
@RequiredArgsConstructor
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void sendSimpleMail(Mail mail) {
        log.info("Starting email preparation...");

        try {
            javaMailSender.send(createMimeMessage(mail));
            log.info("Email has been sent.");
        } catch (MailException me) {
            log.error("Failed to process email sending: " + me.getMessage());
            log.error("Exception stack trace", me);
        }
    }

    /**
     * Here we return lambda. This operation was possible thanks to the fact that the MimeMessagePreparator class is annotated with @FunctionalInterface, which is an interface that allows you to implement behaviors using lambda:
     * @param mail
     * @return lambda expression
     */
    private MimeMessagePreparator createMimeMessage(final Mail mail) { /// Multipurpose Internet Mail Extensions
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    public SimpleMailMessage createSimpleMailMessage(Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCC()).ifPresent(simpleMailMessage::setCc);

        return simpleMailMessage;
    }
}
