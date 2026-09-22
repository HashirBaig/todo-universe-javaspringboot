package com.hashir.todo.user;

public record UserResponse(
        Long id,
        String username
) {
    public static UserResponse fromEntity(User user){

        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }
}