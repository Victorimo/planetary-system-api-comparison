package com.example.planetarysystem.repository;

import com.example.planetarysystem.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Optional<Planet> findByNameIgnoreCase(String name);
    List<Planet> findByTypeIgnoreCase(String type);
}
