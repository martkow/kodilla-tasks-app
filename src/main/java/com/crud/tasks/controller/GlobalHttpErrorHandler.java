package com.crud.tasks.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @AllArgsConstructor
    @Getter // Required to convert Error object to JSON - without does not work, return http code 500
    public static class Error {
        private String level;
        private String title;
        private String code;
        private String description;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    public Error handleTaskNotFoundException(TaskNotFoundException tnfe) {
          return new Error("ERROR", "No task", "task.does.not.exist", "Task with given id does not exist.");
    }


//    @ExceptionHandler(TaskNotFoundException.class)
//    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException tnfe) {
//        return new ResponseEntity<>("Task with given id does not exist.", HttpStatus.BAD_REQUEST);
//    }
}
