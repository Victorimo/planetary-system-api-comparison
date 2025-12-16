package com.planetary.service;

import com.planetary.dto.planet.PlanetRequestDTO;
import com.planetary.dto.planet.PlanetResponseDTO;
import com.planetary.entity.Planet;
import com.planetary.exception.ResourceNotFoundException;
import com.planetary.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlanetService {
    
    private final PlanetRepository planetRepository;
    
    @Transactional
    public PlanetResponseDTO createPlanet(PlanetRequestDTO requestDTO) {
        log.info("Creating new planet: {}", requestDTO.getName());
        
        Planet planet = Planet.builder()
                .name(requestDTO.getName())
                .type(requestDTO.getType())
                .radiusKm(requestDTO.getRadiusKm())
                .massKg(requestDTO.getMassKg())
                .orbitalPeriodDays(requestDTO.getOrbitalPeriodDays())
                .build();
        
        Planet savedPlanet = planetRepository.save(planet);
        log.info("Planet created successfully with ID: {}", savedPlanet.getId());
        
        return mapToResponseDTO(savedPlanet);
    }
    
    public List<PlanetResponseDTO> getAllPlanets() {
        log.info("Fetching all planets");
        return planetRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public PlanetResponseDTO getPlanetById(Long id) {
        log.info("Fetching planet by ID: {}", id);
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + id));
        return mapToResponseDTO(planet);
    }
    
    public List<PlanetResponseDTO> getPlanetsByType(String type) {
        log.info("Fetching planets by type: {}", type);
        return planetRepository.findByType(type)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PlanetResponseDTO updatePlanet(Long id, PlanetRequestDTO requestDTO) {
        log.info("Updating planet with ID: {}", id);
        
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + id));
        
        planet.setName(requestDTO.getName());
        planet.setType(requestDTO.getType());
        planet.setRadiusKm(requestDTO.getRadiusKm());
        planet.setMassKg(requestDTO.getMassKg());
        planet.setOrbitalPeriodDays(requestDTO.getOrbitalPeriodDays());
        
        Planet updatedPlanet = planetRepository.save(planet);
        log.info("Planet updated successfully with ID: {}", updatedPlanet.getId());
        
        return mapToResponseDTO(updatedPlanet);
    }
    
    @Transactional
    public void deletePlanet(Long id) {
        log.info("Deleting planet with ID: {}", id);
        
        if (!planetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Planet not found with ID: " + id);
        }
        
        planetRepository.deleteById(id);
        log.info("Planet deleted successfully with ID: {}", id);
    }
    
    private PlanetResponseDTO mapToResponseDTO(Planet planet) {
        return PlanetResponseDTO.builder()
                .id(planet.getId())
                .name(planet.getName())
                .type(planet.getType())
                .radiusKm(planet.getRadiusKm())
                .massKg(planet.getMassKg())
                .orbitalPeriodDays(planet.getOrbitalPeriodDays())
                .moonCount(planet.getMoons() != null ? planet.getMoons().size() : 0)
                .build();
    }
}

