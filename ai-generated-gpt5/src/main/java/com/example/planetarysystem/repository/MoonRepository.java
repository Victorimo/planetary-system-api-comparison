package com.example.planetarysystem.repository;

import com.example.planetarysystem.domain.Moon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoonRepository extends JpaRepository<Moon, Long> {
    List<Moon> findByPlanetId(Long planetId);
}
