package com.crud.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // For @Bean annotations to work, the class that contains the methods that create the beans must also be annotated with @Configuration.
public class CoreConfiguration {
    @Bean // The @Bean annotation is used to mark methods that produce objects that are intended to become beans.
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
