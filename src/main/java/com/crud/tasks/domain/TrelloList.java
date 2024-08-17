package com.crud.tasks.domain;

import lombok.*;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrelloList {
    private String id;
    private String name;
    private boolean isClosed;
}
