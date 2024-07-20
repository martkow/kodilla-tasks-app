package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/trello")
@Tag(name = "Managing trello boards")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("/boards")
    @Operation(
            description = "Receiving all trello boards",
            summary = "Get boards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Boards received successfully")
    })
    public void getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(trelloBoardDto -> trelloBoardDto.getName() != null && trelloBoardDto.getId() != null)
                .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName() + " " + trelloBoardDto.getLists());
        });
    }

    @Operation(
            description = "Creating a new card in a list",
            summary = "Create a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK - Card created successfully")
    })
    @PostMapping(value = "/cards",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CreatedTrelloCardDto addTrelloCard(@RequestBody TrelloCardDto cardDto) {
        return trelloClient.createTrelloCard(cardDto);
    }
}