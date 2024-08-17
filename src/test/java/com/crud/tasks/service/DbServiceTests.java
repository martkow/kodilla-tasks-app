package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DisplayName("Tests for DBService")
@ExtendWith(MockitoExtension.class)
public class DbServiceTests {
    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository taskRepository;
    private Task task;

    @BeforeEach
    void setUp() {
        // Initialize common test data
        task = new Task(1L, "Test Task", "Test Description");
    }

    @Test
    void shouldReturnAllTasks() {
        // Given
        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        // When
        List<Task> result = dbService.getAllTasks();
        // Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(task, result.get(0));
    }

    @Test
    void shouldReturnTaskById() throws TaskNotFoundException {
        // Given
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        // When
        Task result = dbService.getTask(1L);
        // Then
        Assertions.assertEquals(task, result);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Given
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        // When & Then
        TaskNotFoundException thrown = Assertions.assertThrows(TaskNotFoundException.class, () -> dbService.getTask(1L));
        Assertions.assertEquals("Task with ID 1 does not exist.", thrown.getMessage());
    }

    @Test
    void shouldSaveTask() {
        // Given
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        // When
        Task result = dbService.saveTask(task);
        // Then
        Assertions.assertEquals(task, result);
    }

    @Test
    void shouldDeleteTask() throws TaskNotFoundException {
        // Given
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        // When
        dbService.deleteTask(1L);
        // Then
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        // Given
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        // When & Then
        TaskNotFoundException thrown = Assertions.assertThrows(TaskNotFoundException.class, () -> dbService.deleteTask(1L));
        Assertions.assertEquals("Task with ID 1 does not exist.", thrown.getMessage());
    }
}
