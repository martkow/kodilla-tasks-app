package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@DisplayName("Tests for TrelloFacade class")
@ExtendWith(MockitoExtension.class) // This annotation tells JUnit 5 to enable the Mockito extension, which allows for the use of Mockito's annotations (@Mock, @InjectMocks) to create mock objects and inject them into the class under test.
public class TrelloFacadeTests {
    @InjectMocks // This annotation is used to create an instance of the class under test (TrelloFacade) and inject the mock objects into it.
    private TrelloFacade trelloFacade;
    @Mock // This annotation creates a mock instance of the TrelloMapper class, which is a dependency of the TrelloFacade.
    private TrelloMapper trelloMapper;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloService trelloService;

    @Test
    void shouldReturnEmptyList() {
        // Given
        List<TrelloListDto> trelloListDtos =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardDtos =
                List.of(new TrelloBoardDto("test", "1", trelloListDtos));

        List<TrelloList> trelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> trelloBoards =
                List.of(new TrelloBoard("1", "test", trelloLists));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        Mockito.when(trelloMapper.mapToTrelloBoards(trelloBoardDtos)).thenReturn(trelloBoards);
        Mockito.when(trelloValidator.validateTrelloBoards(trelloBoards)).thenReturn(List.of());
        Mockito.when(trelloMapper.mapToTrelloBoardDtos(ArgumentMatchers.anyList())).thenReturn(List.of());
        // When
        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void shouldReturnTrelloBoards() {
        // Given
        List<TrelloListDto> trelloListDtos =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardDtos =
                List.of(new TrelloBoardDto("test", "1", trelloListDtos));

        List<TrelloList> trelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> trelloBoards =
                List.of(new TrelloBoard("1", "test", trelloLists));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        Mockito.when(trelloMapper.mapToTrelloBoards(trelloBoardDtos)).thenReturn(trelloBoards);
        Mockito.when(trelloValidator.validateTrelloBoards(trelloBoards)).thenReturn(trelloBoards);
        Mockito.when(trelloMapper.mapToTrelloBoardDtos(trelloBoards)).thenReturn(trelloBoardDtos);
        // When
        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        result.forEach(tbd -> {
            Assertions.assertEquals("1", tbd.getId());
            Assertions.assertEquals("test", tbd.getName());

            tbd.getLists().forEach(tld -> {
                Assertions.assertEquals("1", tld.getId());
                Assertions.assertEquals("test_list", tld.getName());
                Assertions.assertFalse(tld.isClosed());
            });
        });
    }

    @Test
    void shouldReturnCreatedTrelloCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "new card", "2", "bottom");
        TrelloCard trelloCard = new TrelloCard("test", "new card", "bottom", "2");
        BadgesDto badgesDto = new BadgesDto(10, new AttachmentsByTypeDto(new TrelloDto(1, 1)));
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("3", "new card", "/test1", "2", "1", badgesDto);

        Mockito.when(trelloMapper.mapToTrelloCard(trelloCardDto)).thenReturn(trelloCard);
        Mockito.when(trelloMapper.mapToTrelloCardDto(trelloCard)).thenReturn(trelloCardDto);
        Mockito.when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        // When
        CreatedTrelloCardDto result = trelloFacade.createTrelloCard(trelloCardDto);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("3", result.getId());
        Assertions.assertEquals("new card", result.getName());
        Assertions.assertEquals("2", result.getIdList());
        Assertions.assertEquals(badgesDto, result.getBadges());
        Assertions.assertEquals("1", result.getIdBoard());
        Assertions.assertEquals("/test1", result.getShortUrl());
    }
}
