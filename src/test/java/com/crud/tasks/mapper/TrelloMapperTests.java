package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DisplayName("Tests for TrelloMapper class")
@SpringBootTest
public class TrelloMapperTests {
    @Autowired
    private TrelloMapper trelloMapper;
    private List<TrelloListDto> trelloListDtos = new ArrayList<>(List.of(
            new TrelloListDto("1", "Test list 1", false),
            new TrelloListDto("2", "Test list 2", true)
        ));
    private List<TrelloList> trelloLists = new ArrayList<>(List.of(
            new TrelloList("1", "Test list 1", false),
            new TrelloList("2", "Test list 2", true)
    ));
    private List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>(List.of(
            new TrelloBoardDto("test board 1","1", trelloListDtos),
            new TrelloBoardDto("test board 2","2", trelloListDtos)
    ));
    private  List<TrelloBoard> trelloBoards = new ArrayList<>(List.of(
            new TrelloBoard("test board 1","1", trelloLists),
            new TrelloBoard("test board 2","2", trelloLists)
    ));
    private TrelloCard trelloCard = new TrelloCard("card", "new card", "bottom", "1");
    private TrelloCardDto trelloCardDto = new TrelloCardDto("card", "new card", "1", "bottom");

    @Test
    void shouldReturnTrelloLists() {
        // Given
        trelloLists.sort(Comparator.comparing(TrelloList::getId));
        // When
        List<TrelloList> result = new ArrayList<>(trelloMapper.mapToTrelloLists(trelloListDtos));
        result.sort(Comparator.comparing(TrelloList::getId));
        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertIterableEquals(trelloLists, result);
    }

    @Test
    void shouldReturnTrelloListDtos() {
        // Given
        trelloListDtos.sort(Comparator.comparing(TrelloListDto::getId));
        // When
        List<TrelloListDto> result = new ArrayList<>(trelloMapper.mapToTrelloListDtos(trelloLists));
        result.sort(Comparator.comparing(TrelloListDto::getId));
        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertIterableEquals(trelloListDtos, result);
    }

    @Test
    void shouldReturnTrelloBoards() {
        // Given
        // When
        List<TrelloBoard> result = new ArrayList<>(trelloMapper.mapToTrelloBoards(trelloBoardDtos));
        // Then
        result.stream().forEach(System.out::println);
    }

    @Test
    void shouldReturnTrelloBoardDtos() {
        // Given
        // When
        List<TrelloBoardDto> result = new ArrayList<>(trelloMapper.mapToTrelloBoardDtos(trelloBoards));
        // Then
        result.stream().forEach(System.out::println);
    }

    @Test
    void shouldReturnTrelloCard() {
        // Given
        // When
        TrelloCard result = trelloMapper.mapToTrelloCard(trelloCardDto);
        // Then
        Assertions.assertEquals(trelloCard, result);
    }

    @Test
    void shouldReturnTrelloCardDto() {
        // Given
        // When
        TrelloCardDto result = trelloMapper.mapToTrelloCardDto(trelloCard);
        // Then
        Assertions.assertEquals(trelloCardDto, result);
    }
}
