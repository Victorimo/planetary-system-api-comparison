package com.example.planetarysystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Explicit provider wiring for UserDetailsService + encoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // H2 console uses frames; allow only for dev convenience.
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        // For this project (Basic Auth, H2 console, GraphiQL), disable CSRF.
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                // Public / tooling endpoints
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // GraphiQL UI can be public so it loads in the browser
                .requestMatchers("/graphiql/**").permitAll()

                // GraphQL endpoint MUST be authenticated so @PreAuthorize can work
                .requestMatchers(HttpMethod.POST, "/graphql").authenticated()

                // Planets & Moons:
                // STUDENT: GET only
                .requestMatchers(HttpMethod.GET, "/planets/**", "/moons/**")
                .hasAnyRole("ADMIN", "STAFF", "STUDENT")

                // STAFF: can create/update/delete planets & moons
                .requestMatchers(HttpMethod.POST, "/planets/**", "/moons/**")
                .hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.PUT, "/planets/**", "/moons/**")
                .hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.DELETE, "/planets/**", "/moons/**")
                .hasAnyRole("ADMIN", "STAFF")

                // Everything else requires authentication
                .anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
