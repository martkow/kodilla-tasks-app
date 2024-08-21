package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.EmailType;
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
    private static final String PREVIEW = "Currently in database you got...";
    private static final String PREFIX = "Currently in database you got: ";
    private final SimpleEmailService simpleEmailService;
    private final AdminConfig adminConfig;
    private final CompanyConfig companyConfig;
    private final TaskRepository taskRepository;

//  @Scheduled(cron = "0 0 10 * * *") // Create a method that Spring will execute at a specified interval. Let's call it sendInformationEmail(). Remember that methods subjected to an interval should not return anything! This is because Spring would not know what to do with such a return value.
@Scheduled(fixedDelay = 30000) // Every 30 seconds
    public void sendInformationEmail() {
        long number = taskRepository.count();
        simpleEmailService.sendSimpleMail(
                Mail.builder()
                        .type(EmailType.NO_REPLY)
                        .to(adminConfig.getAdminMail())
                        .from(companyConfig.getCompanyNoReplyEmail())
                        .subject(SUBJECT)
                        .message(PREFIX + number + (number == 1 ? " task" : " tasks"))
                        .previewMessage(PREVIEW)
                        .build()
        );
    }
}
