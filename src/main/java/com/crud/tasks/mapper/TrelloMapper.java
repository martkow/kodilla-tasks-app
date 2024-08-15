package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloMapper {
    public List<TrelloList> mapToTrelloLists(List<TrelloListDto> trelloListDtos) {
        return trelloListDtos.stream()
                .map( tld -> new TrelloList(
                        tld.getId(),
                        tld.getName(),
                        tld.isClosed()))
                .toList();
    }

    public List<TrelloListDto> mapToTrelloListDtos(List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(tl -> new TrelloListDto(
                        tl.getId(),
                        tl.getName(),
                        tl.isClosed()))
                .toList();
    }

    public List<TrelloBoard> mapToTrelloBoards(List<TrelloBoardDto> trelloBoardDtos) {
        return trelloBoardDtos.stream()
                .map(tbd -> new TrelloBoard(
                        tbd.getId(),
                        tbd.getName(),
                        mapToTrelloLists(tbd.getLists())))
                .toList();
    }

    public List<TrelloBoardDto> mapToTrelloBoardDtos(List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(tb -> new TrelloBoardDto(
                        tb.getName(),
                        tb.getId(),
                        mapToTrelloListDtos(tb.getLists())))
                .toList();
    }

    public TrelloCard mapToTrelloCard(TrelloCardDto trelloCardDto) {
        return new TrelloCard(
                trelloCardDto.getName(),
                trelloCardDto.getDesc(),
                trelloCardDto.getPosition(),
                trelloCardDto.getIdList()
        );
    }

    public TrelloCardDto mapToTrelloCardDto(TrelloCard trelloCard) {
        return new TrelloCardDto(
                trelloCard.getName(),
                trelloCard.getDescription(),
                trelloCard.getListId(),
                trelloCard.getPos()
        );
    }
}
