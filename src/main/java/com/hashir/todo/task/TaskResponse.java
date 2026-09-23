package com.hashir.todo.task;

import com.hashir.todo.user.UserResponse;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        UserResponse user,
        String task,
        LocalDateTime createdDate,
        boolean isCompleted,
        boolean isImportant,
        boolean isEdited
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                UserResponse.fromEntity(task.getUser()),
                task.getTask(),
                task.getCreateDate(),
                task.isCompleted(),
                task.isImportant(),
                task.isEdited()
        );
    }
}