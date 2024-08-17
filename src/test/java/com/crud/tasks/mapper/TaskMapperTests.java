package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Tests for TaskMapper class")
@ExtendWith(MockitoExtension.class)
public class TaskMapperTests {
    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    void shouldReturnTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "title", "content");
        // When
        Task result = taskMapper.mapToTask(taskDto);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("title", result.getTitle());
        Assertions.assertEquals("content", result.getContent());
    }

    @Test
    void shouldReturnTaskDto() {
        // Given
        Task task = new Task(1L, "title", "content");
        // When
        TaskDto result = taskMapper.mapToTaskDto(task);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("title", result.getTitle());
        Assertions.assertEquals("content", result.getContent());
    }

    @Test
    void shouldReturnTaskDtoList() {
        // Given
        List<Task> tasks = new ArrayList<>(List.of(
                new Task(1L, "title", "content"),
                new Task(2L, "title2", "content2")));
        // When
        List<TaskDto> result = taskMapper.mapToTaskDtoList(tasks);
        // Then
        Assertions.assertEquals(2, result.size());
    }
}
