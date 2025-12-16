package com.example.planetarysystem.mapper;

import com.example.planetarysystem.domain.AppUser;
import com.example.planetarysystem.dto.user.UserResponseDTO;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponseDTO toResponse(AppUser entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .role(entity.getRole())
                .build();
    }
}
