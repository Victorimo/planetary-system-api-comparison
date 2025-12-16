package com.example.planetarysystem.service;

import com.example.planetarysystem.domain.AppUser;
import com.example.planetarysystem.domain.Role;
import com.example.planetarysystem.dto.user.UserResponseDTO;
import com.example.planetarysystem.exception.BadRequestException;
import com.example.planetarysystem.exception.ResourceNotFoundException;
import com.example.planetarysystem.mapper.UserMapper;
import com.example.planetarysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Preferred method name for GraphQL resolver usage.
     * Throws ResourceNotFoundException when user does not exist.
     */
    public UserResponseDTO getUserById(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return UserMapper.toResponse(user);
    }

    /**
     * Backwards-compatible alias (optional).
     * You can remove this if nothing else calls it.
     */
    public UserResponseDTO findById(Long id) {
        return getUserById(id);
    }

    @Transactional
    public UserResponseDTO createUser(String username, String rawPassword, Role role) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username must not be blank");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new BadRequestException("Password must not be blank");
        }
        if (role == null) {
            throw new BadRequestException("Role must not be null");
        }

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new BadRequestException("Username already exists: " + username);
        }

        AppUser user = AppUser.builder()
                .username(username.trim())
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .build();

        AppUser saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }
}
