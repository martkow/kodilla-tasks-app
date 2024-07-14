package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @implNote @CrossOrigin("*")<br>
 * Cross Origin Resource Sharing – CORS<br>
 * This annotation tells Spring that it can receive requests from external servers (like our simple MAMP-based frontend server) located under other domains and that it should allow this
*/
@CrossOrigin("*")
@RestController
@RequestMapping("/v1/tasks")
@Tag(name = "Managing tasks")
@RequiredArgsConstructor
public class TaskController {
    private final DbService dbService;
    private final TaskMapper taskMapper;

    @GetMapping
    @Operation(
            description = "Receiving all tasks",
            summary = "Get tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Tasks received successfully")
    })
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = dbService.getAllTasks();

        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }

    @GetMapping(value = "/{taskId}")
    @Operation(
            description = "Receiving a task by taskId",
            summary = "Get a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK - Task received successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request - Task does not exist")
    })
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return ResponseEntity.ok(taskMapper.mapToTaskDto(dbService.getTask(taskId)));
//        return new ResponseEntity<>(taskMapper.mapToTaskDto(dbService.getTask(taskId)), HttpStatus.OK);
    }

//    @GetMapping(value = "/task")
//    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
//        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
//    }

    @DeleteMapping(value = "/{taskId}")
    @Operation(
            description = "Removing a task by taskId",
            summary = "Remove a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK - Task deleted successfully")
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) throws TaskNotFoundException {
        dbService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(
            description = "Updating a task",
            summary = "Update a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK - Task updated successfully")
    })
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = dbService.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Creating a task",
            summary = "Create a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK - Task created successfully")
    })
    public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        dbService.saveTask(task);
        return ResponseEntity.ok().build();
    }
}
