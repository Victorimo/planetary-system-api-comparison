package com.example.planetarysystem.dto.user;

import com.example.planetarysystem.domain.Role;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private Role role;
}
