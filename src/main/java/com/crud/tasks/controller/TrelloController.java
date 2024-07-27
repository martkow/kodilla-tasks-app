package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.service.TrelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/trello")
@Tag(name = "Trello", description = "Managing trello boards")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloService trelloService;

    @Operation(
            description = "Receiving all trello boards",
            summary = "Get boards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Boards received successfully",
                    content = {
                        @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                array = @ArraySchema(schema = @Schema(implementation = TrelloBoardDto.class))
                        )})
    })
    @GetMapping("/boards")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloService.fetchTrelloBoards());
    }

    @Operation(
            description = "Creating a new card in a list",
            summary = "Create a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Card created successfully")
    })
    @PostMapping(value = "/cards",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreatedTrelloCardDto> addTrelloCard(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trello card data to create.", required = true)
            @RequestBody TrelloCardDto cardDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trelloService.createTrelloCard(cardDto));
    }
}