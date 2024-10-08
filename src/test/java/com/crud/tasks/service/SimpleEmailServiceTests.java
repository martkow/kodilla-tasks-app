package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import uk.org.lidalia.slf4jtest.TestLogger;
//import uk.org.lidalia.slf4jtest.TestLoggerFactory;

@DisplayName("Tests for SimpleEmailService")
@ExtendWith(MockitoExtension.class) // This annotation tells JUnit 5 to enable the Mockito extension, which allows for the use of Mockito's annotations (@Mock, @InjectMocks) to create mock objects and inject them into the class under test.
public class SimpleEmailServiceTests {
    @InjectMocks // This annotation is used to create an instance of the class under test (SimpleEmailService) and inject the mock objects into it.
    private SimpleEmailService simpleEmailService;
    @Mock // This annotation creates a mock instance of the JavaMailSender class, which is a dependency of the SimpleEmailService.
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendSimpleEmail() {
        // Given
        Mail mail = Mail.builder()
                .to("test@test.com")
                .subject("New email")
                .text("Testing sending emails by application.")
                .build();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("test@test.com");
        simpleMailMessage.setSubject("New email");
        simpleMailMessage.setText("Testing sending emails by application.");
        // When
        simpleEmailService.sendSimpleMail(mail);
        // Then
        Mockito.verify(javaMailSender, Mockito.times(1)).send(simpleMailMessage);
    }

    @Test
    void shouldThrowMailException() {
        // Given
        Mail mail = Mail.builder()
                .to("test@test.com")
                .subject("New email")
                .text("Testing sending emails by application.")
                .build();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("test@test.com");
        simpleMailMessage.setSubject("New email");
        simpleMailMessage.setText("Testing sending emails by application.");

        Mockito.doThrow(new MailSendException("Failed to send email")).when(javaMailSender).send(simpleMailMessage);
        // When
        simpleEmailService.sendSimpleMail(mail);
        // Then
        Mockito.verify(javaMailSender, Mockito.times(1)).send(simpleMailMessage);

//        TestLogger logger = TestLoggerFactory.getTestLogger(SimpleEmailService.class);
//        logger.getLoggingEvents().forEach(event -> System.out.println(event.getLevel() + " " + event.getMessage()));
//        Assertions.assertTrue(logger.getLoggingEvents().stream()
//                .anyMatch(event -> event.getLevel().toString().equals("ERROR") &&
//                        event.getMessage().equals("Failed to process email sending: Failed to send email")));
    }

    @Test
    void shouldCreateSimpleMailMessageWithoutCC() {
        // Given
        Mail mail = Mail.builder()
                .to("test@test.com")
                .subject("New email")
                .text("Testing sending emails by application.")
                .build();

        String[] array = null;
        // When
        SimpleMailMessage result = simpleEmailService.createSimpleMailMessage(mail);
        // Then
        Assertions.assertEquals(array, result.getCc());
    }

    @Test
    void shouldCreateSimpleMailMessageWithCC() {
        // Given
        Mail mail = Mail.builder()
                .to("test@test.com")
                .subject("New email")
                .text("Testing sending emails by application.")
                .toCC("cc@cc.com")
                .build();

        String[] array = {"cc@cc.com"};
        // When
        SimpleMailMessage result = simpleEmailService.createSimpleMailMessage(mail);
        // Then
        Assertions.assertArrayEquals(array, result.getCc());
    }
}