package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final String SUBJECT =  "Tasks: Once a day email";
    private final SimpleEmailService simpleEmailService;
    private final AdminConfig adminConfig;
    private final TaskRepository taskRepository;

//  @Scheduled(cron = "0 0 10 * * *") // Create a method that Spring will execute at a specified interval. Let's call it sendInformationEmail(). Remember that methods subjected to an interval should not return anything! This is because Spring would not know what to do with such a return value.
// @Scheduled(fixedDelay = 10000) // Every 10 seconds
    public void sendInformationEmail() {
        long number = taskRepository.count();
        System.out.println(adminConfig.getAdminMail());
        simpleEmailService.sendSimpleMail(
                Mail.builder()
                        .to(adminConfig.getAdminMail())
                        .subject(SUBJECT)
                        .text("Currently in database you got: " + number + (number == 1 ? " task" : " tasks"))
                        .build()
        );
    }
}
