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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "{taskId}")
    @Operation(
            description = "Receiving a task by taskId",
            summary = "Get a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Task received successfully")
    })
    public TaskDto getTask(@PathVariable Long taskId) {
        Task task = dbService.getTask(taskId);

        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping
    public void deleteTask(Long taskId) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping
    public void createTask(TaskDto taskDto) {

    }
}
