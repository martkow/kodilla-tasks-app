package com.crud.tasks.trello.client;

import com.crud.tasks.domain.BadgesDto;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class) // This is a way to launch a context that allows you to create mocks.
class TrelloClientTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClientTests.class);
    @InjectMocks // An annotation that creates a real object. The remaining fields of the testing class marked with the @Mock annotation will be injected into it.
    private TrelloClient trelloClient;
    @Mock // An annotation that creates an artificial object, allowing to induce artificial behavior for testing purposes.
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException { // This is one of the conventions for writing test names, in which the name of a method assumes how it should work.
        // Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        Mockito.when(trelloConfig.getTrelloUser()).thenReturn("me");
        Mockito.when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");

//        URI url = new URI("https://test.com/members/me/boards?key=test&token=test&fields=name,id&lists=all");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("Kodilla", "test_id", new ArrayList<>());

        Mockito.when(restTemplate.getForObject(trelloClient.buildUriGetBoards(), TrelloBoardDto[].class)).thenReturn(trelloBoards);
        // When
        LOGGER.info("Starting test shouldFetchTrelloBoards.");
        List<TrelloBoardDto> result = trelloClient.getTrelloBoards();
        // Then
        LOGGER.info("Checking assertions.");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("test_id", result.get(0).getId());
        Assertions.assertEquals("Kodilla", result.get(0).getName());
        Assertions.assertEquals(new ArrayList<>(), result.get(0).getLists());
    }

    @Test
    public void shouldReturnEmptyListWhenNull() {
        // Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        Mockito.when(trelloConfig.getTrelloUser()).thenReturn("me");
        Mockito.when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");

        Mockito.when(restTemplate.getForObject(trelloClient.buildUriGetBoards(), TrelloBoardDto[].class)).thenReturn(null);
        // When
        List<TrelloBoardDto> result = trelloClient.getTrelloBoards();
        // Then
        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void shouldReturnEmptyListWhenException() {
        // Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        Mockito.when(trelloConfig.getTrelloUser()).thenReturn("me");
        Mockito.when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");

        Mockito.when(restTemplate.getForObject(trelloClient.buildUriGetBoards(), TrelloBoardDto[].class)).thenThrow(RestClientException.class);
        // When
        List<TrelloBoardDto> result = trelloClient.getTrelloBoards();
        // Then
        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void shouldCreateTrelloCard() {
        // Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        Mockito.when(trelloConfig.getTrelloApiKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");

        TrelloCardDto trelloCardDto = new TrelloCardDto("task", "task description", "456", "top");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("123", "task", "https://test/abc.com", "456", "789", new BadgesDto());

        Mockito.when(restTemplate.postForObject(trelloClient.buildUriAddCard(), trelloCardDto, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        // When
        CreatedTrelloCardDto result = trelloClient.createTrelloCard(trelloCardDto);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("task", result.getName());
        Assertions.assertEquals("456", result.getIdList());
    }
}