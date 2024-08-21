package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder // The @Builder annotation in Lombok provides an easy and efficient way to implement the Builder design pattern in Java. It automatically generates the builder class and methods. You can combine @Builder with @NonNull to ensure that certain fields are required: @NonNull private String field;
@Getter
public class Mail {
    @NonNull
    private String to;
    private String subject;
    private String previewMessage;
    private String message;
    private String toCC;
    private String from;
    private EmailType type;
}
