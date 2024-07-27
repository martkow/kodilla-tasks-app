package com.crud.tasks.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Task") // Use @Schema annotations to provide descriptions for the fields.
public class TaskDto {
    @Schema(description = "Unique identifier of the task", example = "1", accessMode = Schema.AccessMode.READ_ONLY) // accessMode = Schema.AccessMode.READ_ONLY: This makes the id field read-only and won't be displayed in the request body of the Swagger documentation. It indicates that id is not expected to be set by the user in the request body.
    private Long id;
    @Schema(description = "Title of the task", example = "Task title.")
    private String title;
    @Schema(description = "Description of the task", example = "Task description.")
    private String content;
}
