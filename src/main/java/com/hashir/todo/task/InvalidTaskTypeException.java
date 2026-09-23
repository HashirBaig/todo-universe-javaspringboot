package com.hashir.todo.task;

public class InvalidTaskTypeException extends RuntimeException {
    public InvalidTaskTypeException(String taskType) {
        super("Invalid task_type: '" + taskType + "'. Must be one of: all, active, completed");
    }
}