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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();

        return taskMapper.mapToTaskDtoList(tasks);
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
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) {
        try {
            return new ResponseEntity<>(taskMapper.mapToTaskDto(dbService.getTask(taskId)), HttpStatus.OK);
        } catch (TaskNotFoundException tnfe) {
            return new ResponseEntity<>(new TaskDto(0L, "There is no task with id equal to: " + taskId, ""), HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping(value = "/task")
//    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
//        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
//    }

    @DeleteMapping
    public void deleteTask(Long taskId) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        dbService.saveTask(task);
    }
}
