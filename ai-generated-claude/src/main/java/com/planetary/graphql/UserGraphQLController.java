package com.planetary.graphql;

import com.planetary.dto.user.UserRequestDTO;
import com.planetary.dto.user.UserResponseDTO;
import com.planetary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserGraphQLController {
    
    private final UserService userService;
    
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO findUserById(@Argument Long id) {
        log.info("GraphQL Query: findUserById with id={}", id);
        return userService.getUserById(id);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO createUser(
            @Argument String username,
            @Argument String password,
            @Argument String role) {
        log.info("GraphQL Mutation: createUser with username={}, role={}", username, role);
        
        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        
        return userService.createUser(requestDTO);
    }
}

