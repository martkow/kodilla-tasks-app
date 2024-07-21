package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Annotation with the same behavior as @Component, but we apply it to beans that provide some more complex services.
@RequiredArgsConstructor // Create constructor for all class fields marked as 'final'
public class DbService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(final Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public Task saveTask(final Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(final Long id) throws TaskNotFoundException {
        taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
    }
}
