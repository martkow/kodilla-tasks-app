package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Tests for EmailScheduler class")
@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTests {
    @InjectMocks
    private EmailScheduler emailScheduler;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TaskRepository taskRepository;

    @Test
    void shouldSendInformationEmailWithCorrectDetailsWhenCountTaskIsEqualOne() {
        // Given
        Mockito.when(taskRepository.count()).thenReturn(1L);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
        System.out.println(adminConfig.getAdminMail());
        // When
        emailScheduler.sendInformationEmail();
        // Then
        Mockito.verify(simpleEmailService, Mockito.times(1)).sendSimpleMail(
                ArgumentMatchers.argThat(mail ->
                        "admin@example.com".equals(mail.getTo()) &&
                                "Tasks: Once a day email".equals(mail.getSubject()) &&
                                "Currently in database you got: 1 task".equals(mail.getText())
                )
        );
    }

    @Test
    void shouldSendInformationEmailWithCorrectDetailsWhenCountTaskIsMoreThanOne() {
        // Given
        Mockito.when(taskRepository.count()).thenReturn(2L);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
        System.out.println(adminConfig.getAdminMail());
        // When
        emailScheduler.sendInformationEmail();
        // Then
        Mockito.verify(simpleEmailService, Mockito.times(1)).sendSimpleMail(
                ArgumentMatchers.argThat(mail ->
                        "admin@example.com".equals(mail.getTo()) &&
                                "Tasks: Once a day email".equals(mail.getSubject()) &&
                                "Currently in database you got: 2 tasks".equals(mail.getText())
                )
        );
    }
}
