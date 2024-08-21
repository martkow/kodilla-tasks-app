package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrelloService {
    private static final String SUBJECT = "Tasks: New Trello card.";
    private static final String PREFIX = "New card: ";
    private static final String SUFFIX = "...";
    private static final String SUFFIX2 = " has been created on your Trello account.";
    private final TrelloClient trelloClient;
    private final SimpleEmailService simpleEmailService;
    private final AdminConfig adminConfig;
    private final CompanyConfig companyConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createTrelloCard(trelloCardDto);

        Optional.ofNullable(newCard)
                .ifPresent(nc ->
                        simpleEmailService.sendSimpleMail(
                                Mail.builder()
                                        .type(EmailType.NEW_CARD)
                                        .to(adminConfig.getAdminMail())
                                        .from(companyConfig.getCompanyEmail())
                                        .subject(SUBJECT)
                                        .previewMessage(PREFIX + trelloCardDto.getName() + SUFFIX)
                                        .message(PREFIX + trelloCardDto.getName() + SUFFIX2)
                                        .build()
                        ));

        return newCard;
    }
}
