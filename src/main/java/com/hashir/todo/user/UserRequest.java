package com.hashir.todo.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String username
) {
}