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

@RestControllerAdvice // This annotation tells Spring that this class will be used for global exception handling for controllers.
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler { // extends ResponseEntityExceptionHandler: Extending this class allows customization of Spring's default error handling behavior.
    @AllArgsConstructor // @AllArgsConstructor: Lombok annotation that generates a constructor with all the fields as parameters.
    @Getter // @Getter: Lombok annotation that generates getter methods for all the fields. This is necessary for Spring to correctly serialize the Error object to JSON.
    public static class Error { // Error: An inner class representing the structure of an error.
        private String level;
        private String title;
        private String code;
        private String description;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // @ResponseStatus(HttpStatus.BAD_REQUEST): Sets the HTTP status code of the response to 400 Bad Request.
    @ResponseBody // @ResponseBody: Indicates that the return value of the method should be written directly to the HTTP response body.
    @ExceptionHandler(TaskNotFoundException.class) // @ExceptionHandler(TaskNotFoundException.class): This annotation indicates that this method will handle exceptions of type TaskNotFoundException.
    public Error handleTaskNotFoundException(TaskNotFoundException tnfe) { // handleTaskNotFoundException(TaskNotFoundException tnfe): The method that is called when a TaskNotFoundException is thrown. It creates and returns a new Error object.
          return new Error("ERROR", "No task", "task.does.not.exist", "Task with ID " + tnfe.getTaskId() + " does not exist.");
    }


//    @ExceptionHandler(TaskNotFoundException.class)
//    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException tnfe) {
//        return new ResponseEntity<>("Task with given id does not exist.", HttpStatus.BAD_REQUEST);
//    }
}
