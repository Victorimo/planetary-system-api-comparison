package ie.spring.planetary.dtos.user;

import ie.spring.planetary.enums.Role;

import java.time.LocalDateTime;

public record UserResponseDto(
    int userId,
    String username,
    Role role,
    boolean enabled,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}

