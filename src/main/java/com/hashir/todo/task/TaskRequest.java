package com.hashir.todo.task;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
    @NotBlank String task,
    Boolean isCompleted,
    Boolean isImportant,
    Boolean isEdited
) {
}