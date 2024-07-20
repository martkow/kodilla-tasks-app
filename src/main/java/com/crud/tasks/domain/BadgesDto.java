package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BadgesDto {
    @JsonProperty("vote")
    private int vote;
    @JsonProperty("attachmentsByType")
    private AttachmentsByTypeDto attachmentsByType;
}
