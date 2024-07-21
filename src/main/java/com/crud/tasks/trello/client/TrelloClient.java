package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component // A basic annotation denoting the class whose object is to become a bean. It is most often used for simple classes that do not have dependencies to other classes.
@RequiredArgsConstructor // Create constructor for all class fields marked as 'final'
// Dependency injection of RestTemplate by constructor because of adding @RequiredArgsConstructor.
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;

    public List<TrelloBoardDto> getTrelloBoards() {
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
//                 trelloApiEndpoint + "/members/me/boards" + "?key=" + trelloApiKey + "&token=" + trelloToken + "&=fields=name,id",
//                TrelloBoardDto[].class);
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUriGetBoards(), TrelloBoardDto[].class);

            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(trelloBoardDto -> trelloBoardDto.getName() != null && Objects.nonNull(trelloBoardDto.getId()))
                    .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                    .collect(Collectors.toList());
        } catch (RestClientException rce) {
            LOGGER.error(rce.getMessage(), rce);
            return Collections.emptyList();
        }
    }

    URI buildUriGetBoards() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUser() + "/boards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }

    public CreatedTrelloCardDto createTrelloCard(TrelloCardDto trelloCardDto) {
            return restTemplate.postForObject(buildUriAddCard(), trelloCardDto, CreatedTrelloCardDto.class); // TODO -> handle possible exceptions
    }

    public URI buildUriAddCard() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloApiKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .build()
                .encode()
                .toUri();
    }
}
