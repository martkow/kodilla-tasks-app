package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
//import uk.org.lidalia.slf4jtest.TestLogger;
//import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTests {
    @InjectMocks
    private TrelloValidator trelloValidator;
//    private TestLogger logger = TestLoggerFactory.getTestLogger(TrelloValidator.class);

    @Test
    void shouldValidateTrelloBoards() {
        // Given
        List<TrelloList> trelloLists =
                List.of(
                        new TrelloList("1", "test_list", false));

        List<TrelloBoard> trelloBoards =
                List.of(
                        new TrelloBoard("1", "test", trelloLists),
                        new TrelloBoard("2", "board", trelloLists));
        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(trelloBoards);
        // Then
        Assertions.assertEquals(1, result.size());
        result.forEach(tb -> {
            Assertions.assertEquals("2", tb.getId());
            Assertions.assertEquals("board", tb.getName());
            Assertions.assertEquals(trelloLists, tb.getLists());
        });
    }

    @Test
    void shouldLogTestingMessageWhenCardNameContainsTest() {
        // Given
        TrelloCard trelloCard = new TrelloCard("test card", "description", "pos", "listId");
        // When
        trelloValidator.validateTrelloCard(trelloCard);
        // Then
//        Assertions.assertTrue(logger.isInfoEnabled());
//        logger.getLoggingEvents().forEach(event -> System.out.println(event.getLevel() + " " + event.getMessage()));
//        Assertions.assertTrue(logger.getLoggingEvents().stream()
//                .anyMatch(event -> event.getLevel().toString().equals("INFO") &&
//                        event.getMessage().equals("Someone is testing my application!")));
    }

    @Test
    void shouldLogProperUsageMessageWhenCardNameDoesNotContainTest() {
        // Given
        TrelloCard trelloCard = new TrelloCard("normal card", "description", "pos", "listId");
        // When
        trelloValidator.validateTrelloCard(trelloCard);
        // Then
//        Assertions.assertTrue(logger.getLoggingEvents().stream()
//                .anyMatch(event -> event.getLevel().toString().equals("INFO") &&
//                        event.getMessage().equals("Seems that my application is used in proper way.")));
    }
}

