package com.planetary.config;

import com.planetary.entity.Moon;
import com.planetary.entity.Planet;
import com.planetary.entity.User;
import com.planetary.repository.MoonRepository;
import com.planetary.repository.PlanetRepository;
import com.planetary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PlanetRepository planetRepository;
    private final MoonRepository moonRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");
        
        // Create Users
        createUsers();
        
        // Create Planets and Moons
        createPlanetsAndMoons();
        
        log.info("Data initialization completed successfully!");
    }
    
    private void createUsers() {
        log.info("Creating sample users...");
        
        // Admin User
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(User.UserRole.ADMIN)
                .build();
        userRepository.save(admin);
        log.info("Created user: {} with role: {}", admin.getUsername(), admin.getRole());
        
        // Staff User
        User staff = User.builder()
                .username("staff")
                .password(passwordEncoder.encode("staff123"))
                .role(User.UserRole.STAFF)
                .build();
        userRepository.save(staff);
        log.info("Created user: {} with role: {}", staff.getUsername(), staff.getRole());
        
        // Student User
        User student = User.builder()
                .username("student")
                .password(passwordEncoder.encode("student123"))
                .role(User.UserRole.STUDENT)
                .build();
        userRepository.save(student);
        log.info("Created user: {} with role: {}", student.getUsername(), student.getRole());
    }
    
    private void createPlanetsAndMoons() {
        log.info("Creating sample planets and moons...");
        
        // Create Earth
        Planet earth = Planet.builder()
                .name("Earth")
                .type("Terrestrial")
                .radiusKm(6371.0)
                .massKg(5.972e24)
                .orbitalPeriodDays(365.25)
                .build();
        earth = planetRepository.save(earth);
        log.info("Created planet: {}", earth.getName());
        
        // Create Earth's Moon
        Moon earthMoon = Moon.builder()
                .name("Moon")
                .diameterKm(3474.8)
                .orbitalPeriodDays(27.3)
                .planet(earth)
                .build();
        moonRepository.save(earthMoon);
        log.info("Created moon: {} orbiting {}", earthMoon.getName(), earth.getName());
        
        // Create Mars
        Planet mars = Planet.builder()
                .name("Mars")
                .type("Terrestrial")
                .radiusKm(3389.5)
                .massKg(6.417e23)
                .orbitalPeriodDays(687.0)
                .build();
        mars = planetRepository.save(mars);
        log.info("Created planet: {}", mars.getName());
        
        // Create Mars' Moons
        Moon phobos = Moon.builder()
                .name("Phobos")
                .diameterKm(22.4)
                .orbitalPeriodDays(0.319)
                .planet(mars)
                .build();
        moonRepository.save(phobos);
        log.info("Created moon: {} orbiting {}", phobos.getName(), mars.getName());
        
        Moon deimos = Moon.builder()
                .name("Deimos")
                .diameterKm(12.4)
                .orbitalPeriodDays(1.263)
                .planet(mars)
                .build();
        moonRepository.save(deimos);
        log.info("Created moon: {} orbiting {}", deimos.getName(), mars.getName());
        
        // Create Jupiter
        Planet jupiter = Planet.builder()
                .name("Jupiter")
                .type("Gas Giant")
                .radiusKm(69911.0)
                .massKg(1.898e27)
                .orbitalPeriodDays(4333.0)
                .build();
        jupiter = planetRepository.save(jupiter);
        log.info("Created planet: {}", jupiter.getName());
        
        // Create some of Jupiter's major moons
        Moon io = Moon.builder()
                .name("Io")
                .diameterKm(3643.2)
                .orbitalPeriodDays(1.769)
                .planet(jupiter)
                .build();
        moonRepository.save(io);
        log.info("Created moon: {} orbiting {}", io.getName(), jupiter.getName());
        
        Moon europa = Moon.builder()
                .name("Europa")
                .diameterKm(3121.6)
                .orbitalPeriodDays(3.551)
                .planet(jupiter)
                .build();
        moonRepository.save(europa);
        log.info("Created moon: {} orbiting {}", europa.getName(), jupiter.getName());
        
        Moon ganymede = Moon.builder()
                .name("Ganymede")
                .diameterKm(5268.2)
                .orbitalPeriodDays(7.155)
                .planet(jupiter)
                .build();
        moonRepository.save(ganymede);
        log.info("Created moon: {} orbiting {}", ganymede.getName(), jupiter.getName());
    }
}

