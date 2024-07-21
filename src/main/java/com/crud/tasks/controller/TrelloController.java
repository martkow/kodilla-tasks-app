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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloClient.getTrelloBoards());
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
    public ResponseEntity<CreatedTrelloCardDto> addTrelloCard(@RequestBody TrelloCardDto cardDto) {
        return ResponseEntity.ok(trelloClient.createTrelloCard(cardDto));
    }
}