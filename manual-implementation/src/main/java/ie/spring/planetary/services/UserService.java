package ie.spring.planetary.services;

import ie.spring.planetary.dtos.user.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(int id);
    UserResponseDto createUser(String username, String password, String role, Boolean enabled);
}

