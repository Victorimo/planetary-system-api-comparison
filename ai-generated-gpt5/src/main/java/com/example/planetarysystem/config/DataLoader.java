package com.example.planetarysystem.config;

import com.example.planetarysystem.domain.*;
import com.example.planetarysystem.repository.MoonRepository;
import com.example.planetarysystem.repository.PlanetRepository;
import com.example.planetarysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PlanetRepository planetRepository;
    private final MoonRepository moonRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build());

            userRepository.save(AppUser.builder()
                    .username("staff")
                    .password(passwordEncoder.encode("staff123"))
                    .role(Role.STAFF)
                    .build());

            userRepository.save(AppUser.builder()
                    .username("student")
                    .password(passwordEncoder.encode("student123"))
                    .role(Role.STUDENT)
                    .build());
        }

        if (planetRepository.count() == 0) {
            Planet earth = planetRepository.save(Planet.builder()
                    .name("Earth")
                    .type("Terrestrial")
                    .radiusKm(6371.0)
                    .massKg(5.972e24)
                    .orbitalPeriodDays(365.25)
                    .build());

            Planet mars = planetRepository.save(Planet.builder()
                    .name("Mars")
                    .type("Terrestrial")
                    .radiusKm(3389.5)
                    .massKg(6.417e23)
                    .orbitalPeriodDays(686.98)
                    .build());

            moonRepository.save(Moon.builder()
                    .name("Moon")
                    .diameterKm(3474.8)
                    .orbitalPeriodDays(27.32)
                    .planet(earth)
                    .build());

            moonRepository.save(Moon.builder()
                    .name("Phobos")
                    .diameterKm(22.2)
                    .orbitalPeriodDays(0.3189)
                    .planet(mars)
                    .build());

            moonRepository.save(Moon.builder()
                    .name("Deimos")
                    .diameterKm(12.4)
                    .orbitalPeriodDays(1.263)
                    .planet(mars)
                    .build());
        }
    }
}
