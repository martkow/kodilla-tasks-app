package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Tests for TrelloService class")
@ExtendWith(MockitoExtension.class)
public class TrelloServiceTests {
    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldReturnTrelloBoards() {
        // Given
        List<TrelloListDto> trelloListDtos =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>(List.of(
                new TrelloBoardDto("name", "id", trelloListDtos)));

        Mockito.when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        // Then
        Mockito.verify(trelloClient, Mockito.times(1)).getTrelloBoards();
        result.forEach(tb -> {
            Assertions.assertEquals("id", tb.getId());
            Assertions.assertEquals("name", tb.getName());
            tb.getLists().forEach(l -> {
                Assertions.assertEquals("1", l.getId());
                Assertions.assertEquals("test_list", l.getName());
                Assertions.assertFalse(l.isClosed());
            });
        });
    }

    @Test
    void shouldCreateTrelloCardAndSendEmail() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Description", "idList", "position");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("cardId", "Test Card", "shortUrl", "listId", "idBoard", null);

        Mockito.when(trelloClient.createTrelloCard(trelloCardDto)).thenReturn(createdCard);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);
        // Then
        Mockito.verify(trelloClient, Mockito.times(1)).createTrelloCard(trelloCardDto);
        Mockito.verify(simpleEmailService, Mockito.times(1)).sendSimpleMail(
                ArgumentMatchers.argThat(mail ->
                        "admin@example.com".equals(mail.getTo()) &&
                                "Tasks: New Trello card.".equals(mail.getSubject()) &&
                                "New card: Test Card has been created on your Trello account.".equals(mail.getText())
                )
        );
    }

    @Test
    void shouldNotSendEmailIfCardCreationFails() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Description", "idList", "position");

        Mockito.when(trelloClient.createTrelloCard(trelloCardDto)).thenReturn(null);
        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);
        // Then
        Mockito.verify(trelloClient, Mockito.times(1)).createTrelloCard(trelloCardDto);
        Mockito.verify(simpleEmailService, Mockito.never()).sendSimpleMail(ArgumentMatchers.any(Mail.class));
        Assertions.assertNull(result);
    }
}

