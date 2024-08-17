package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

/**
 * @implNote @CrossOrigin("*")<br>
 * Cross Origin Resource Sharing – CORS<br>
 * This annotation tells Spring that it can receive requests from external servers (like our simple MAMP-based frontend server) located under other domains and that it should allow this
*/
@CrossOrigin(origins = "*")
@RestController // Annotation most often used when writing the so-called controllers for web applications. Practically identical to the @Component annotation, but it is worth using it for web service controllers.
@RequestMapping("/v1/tasks")
@Tag(name = "Tasks", description = "Managing tasks")
@RequiredArgsConstructor
public class TaskController {
    private final DbService dbService;
    private final TaskMapper taskMapper;

    @Operation(
            description = "Receiving all tasks",
            summary = "Get tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Tasks received successfully.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TaskDto.class))
            ))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = dbService.getAllTasks();

        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }

    @Operation(
            description = "Receiving a task by taskId",
            summary = "Get a task")
    @ApiResponses(value = { // @ApiResponses: Specifies the possible responses the method can return.
            @ApiResponse(responseCode = "200", // @ApiResponse(responseCode = "200", ...) defines the response for a successful request (status 200).
                    description = "Task received successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class),
                            examples = @ExampleObject(value = "{\"id\": 1, \"title\": \"New Task\", \"content\": \"New Content\"}")
                    )),
            @ApiResponse(responseCode = "400", // @ApiResponse(responseCode = "400", ...) defines the response for an error (status 400). @Content with @Schema describes the response structure for this status code.
                    description = "Task with ID {taskId} does not exist.",
                    content = @Content( // @Content: Specifies the type and schema of the response.
                            mediaType = "application/json", // mediaType = "application/json" indicates that the response will be in JSON format.
                            schema = @Schema(implementation = GlobalHttpErrorHandler.Error.class) // schema = @Schema(implementation = Error.class) indicates that the response will have the structure of the Error class.
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(
            @Parameter(description = "Task Id", example = "1")
            @PathVariable Long taskId) throws TaskNotFoundException {
        return ResponseEntity.ok(taskMapper.mapToTaskDto(dbService.getTask(taskId)));
//        return new ResponseEntity<>(taskMapper.mapToTaskDto(dbService.getTask(taskId)), HttpStatus.OK);
    }

//    @GetMapping(value = "/task")
//    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
//        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
//    }

    @Operation(
            description = "Removing a task by taskId",
            summary = "Remove a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Task deleted successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Task with ID {taskId} does not exist.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GlobalHttpErrorHandler.Error.class)
                    ))
    })
    @DeleteMapping(value = "/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task Id", example = "1")
            @PathVariable Long taskId) throws TaskNotFoundException {
        dbService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Updating a task",
            summary = "Update a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Task updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)
                    ))
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDto> updateTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Task data to update.", required = true)
            @RequestBody TaskDto taskDto
    ) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = dbService.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

    @Operation(
            description = "Creating a task",
            summary = "Create a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Task created successfully"
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Task data to create.", required = true)
            @RequestBody TaskDto taskDto
    ) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = dbService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
