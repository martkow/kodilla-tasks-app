package com.crud.tasks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfig {
    @Value("${admin.mail}")
    private String adminMail;
}
