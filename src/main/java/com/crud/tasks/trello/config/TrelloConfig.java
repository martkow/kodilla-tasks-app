package com.crud.tasks.trello.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TrelloConfig {
    @Value("${trello.api.endpoint.prod}") // Inside the @Value annotation placed on the class field, we enter the field name in application.properties according to the adopted schema: ${here_field_name}
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloApiKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUser;
}
