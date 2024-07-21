package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // Tells the application not to pay attention to parameters included in the JSON response if they are not in the class as fields. Assigning only what we will use to our object, annotating @JsonIgnoreProperties(ignoreUnknown = true) and ignoring the remaining fields from the server's response. The consequence of not using this annotation is that Spring will throw an exception, saying that the response from the server is different than the one we want to map to the object.
public class TrelloListDto {

    @JsonProperty("id") // The annotation, which takes the exact field name in the JSON response in parentheses
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("closed")
    private boolean isClosed;
}
