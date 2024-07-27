package com.crud.tasks.controller;

import lombok.Getter;

@Getter
public class TaskNotFoundException extends Exception {
    private Long taskId;

    public TaskNotFoundException(Long taskId) {
        super("Task with ID " + taskId + " does not exist.");
        this.taskId = taskId;
    }
}
