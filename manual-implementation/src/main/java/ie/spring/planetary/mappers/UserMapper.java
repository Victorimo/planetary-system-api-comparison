package ie.spring.planetary.mappers;

import ie.spring.planetary.dtos.user.UserResponseDto;
import ie.spring.planetary.entities.User;

public class UserMapper {

    public static UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

