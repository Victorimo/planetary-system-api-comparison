package ie.spring.planetary.services;

import ie.spring.planetary.dtos.user.UserResponseDto;
import ie.spring.planetary.entities.User;
import ie.spring.planetary.enums.Role;
import ie.spring.planetary.exceptions.DuplicateEntityException;
import ie.spring.planetary.exceptions.NotFoundException;
import ie.spring.planetary.mappers.UserMapper;
import ie.spring.planetary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return UserMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto createUser(String username, String password, String role, Boolean enabled) {
        // Check if user with same username already exists
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new DuplicateEntityException("User with username '" + username + "' already exists");
        }

        // Validate role
        Role userRole;
        try {
            userRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(userRole);
        user.setEnabled(enabled != null ? enabled : true);

        User savedUser = userRepository.save(user);
        return UserMapper.toResponseDto(savedUser);
    }
}

