package com.crud.tasks;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfigDebugger {

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @PostConstruct
    public void logMailConfig() {
        System.out.println("Mail Username: " + mailUsername);
        System.out.println("Mail Password: " + mailPassword);
    }
}
