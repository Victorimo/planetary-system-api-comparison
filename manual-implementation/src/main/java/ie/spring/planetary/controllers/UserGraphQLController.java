package ie.spring.planetary.controllers;

import ie.spring.planetary.dtos.user.UserResponseDto;
import ie.spring.planetary.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserGraphQLController {

    private final UserService userService;

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto findUserById(@Argument int id) {
        return userService.getUserById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto createUser(@Argument("input") UserInput input) {
        return userService.createUser(
                input.username(),
                input.password(),
                input.role(),
                input.enabled()
        );
    }

    public record UserInput(
            String username,
            String password,
            String role,
            Boolean enabled
    ) {}
}

