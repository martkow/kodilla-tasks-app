package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component // A basic annotation denoting the class whose object is to become a bean. It is most often used for simple classes that do not have dependencies to other classes.
@RequiredArgsConstructor // Create constructor for all class fields marked as 'final'
// Dependency injection of RestTemplate by constructor because of adding @RequiredArgsConstructor.
public class TrelloClient {
    private final RestTemplate restTemplate;
    @Value("${trello.api.endpoint.prod}") // Inside the @Value annotation placed on the class field, we enter the field name in application.properties according to the adopted schema: ${here_field_name}
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloApiKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUser;

    public List<TrelloBoardDto> getTrelloBoards() {
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
//                 trelloApiEndpoint + "/members/me/boards" + "?key=" + trelloApiKey + "&token=" + trelloToken + "&=fields=name,id",
//                TrelloBoardDto[].class);

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUri(), TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(new ArrayList<>());
    }

    private URI buildUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUser + "/boards")
                .queryParam("key", trelloApiKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();
    }
}
