package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyConfig {
    @Value("${info.company.name}")
    private String companyName;
    @Value("${info.company.email}")
    private String companyEmail;
    @Value("${info.company.noreply.email}")
    private String companyNoReplyEmail;
    @Value("${info.company.phone}")
    private String companyPhone;
    @Value("${info.company.goal}")
    private String companyGoal;
}
