package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@DisplayName("Tests for TaskController class")
@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchEmptyList() throws Exception {
        // Given
        Mockito.when(dbService.getAllTasks()).thenReturn(List.of());
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTasks() throws Exception {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Title", "Content"));
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, "Title", "Content"));

        Mockito.when(dbService.getAllTasks()).thenReturn(tasks);
        Mockito.when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Content")));
    }

    @Test
    void shouldFetchTask() throws Exception {
        // Given
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        Mockito.when(dbService.getTask(1L)).thenReturn(task);
        Mockito.when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")));
    }

    @Test
    void shouldReturnTaskNotFoundExceptionWhenFetchingTask() throws Exception {
        // Given
        Mockito.when(dbService.getTask(1L)).thenThrow(new TaskNotFoundException(1L));
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("No task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("task.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Task with ID 1 does not exist.")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // Given
        Mockito.doNothing().when(dbService).deleteTask(1L);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldReturnTaskNotFoundExceptionWhenDeletingTask() throws Exception {
        // Given
        Mockito.doThrow(new TaskNotFoundException(1L)).when(dbService).deleteTask(1L);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("No task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("task.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Task with ID 1 does not exist.")));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        Mockito.when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        Mockito.when(dbService.saveTask(task)).thenReturn(task);
        Mockito.when(taskMapper.mapToTaskDto(Mockito.any())).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        Mockito.when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        Mockito.when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }
}
