package com.planetary.service;

import com.planetary.dto.moon.MoonRequestDTO;
import com.planetary.dto.moon.MoonResponseDTO;
import com.planetary.entity.Moon;
import com.planetary.entity.Planet;
import com.planetary.exception.ResourceNotFoundException;
import com.planetary.repository.MoonRepository;
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
public class MoonService {
    
    private final MoonRepository moonRepository;
    private final PlanetRepository planetRepository;
    
    @Transactional
    public MoonResponseDTO createMoon(MoonRequestDTO requestDTO) {
        log.info("Creating new moon: {}", requestDTO.getName());
        
        Planet planet = planetRepository.findById(requestDTO.getPlanetId())
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + requestDTO.getPlanetId()));
        
        Moon moon = Moon.builder()
                .name(requestDTO.getName())
                .diameterKm(requestDTO.getDiameterKm())
                .orbitalPeriodDays(requestDTO.getOrbitalPeriodDays())
                .planet(planet)
                .build();
        
        Moon savedMoon = moonRepository.save(moon);
        log.info("Moon created successfully with ID: {}", savedMoon.getId());
        
        return mapToResponseDTO(savedMoon);
    }
    
    public List<MoonResponseDTO> getAllMoons() {
        log.info("Fetching all moons");
        return moonRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public MoonResponseDTO getMoonById(Long id) {
        log.info("Fetching moon by ID: {}", id);
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found with ID: " + id));
        return mapToResponseDTO(moon);
    }
    
    public List<MoonResponseDTO> getMoonsByPlanetId(Long planetId) {
        log.info("Fetching moons by planet ID: {}", planetId);
        return moonRepository.findByPlanetId(planetId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MoonResponseDTO updateMoon(Long id, MoonRequestDTO requestDTO) {
        log.info("Updating moon with ID: {}", id);
        
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found with ID: " + id));
        
        Planet planet = planetRepository.findById(requestDTO.getPlanetId())
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + requestDTO.getPlanetId()));
        
        moon.setName(requestDTO.getName());
        moon.setDiameterKm(requestDTO.getDiameterKm());
        moon.setOrbitalPeriodDays(requestDTO.getOrbitalPeriodDays());
        moon.setPlanet(planet);
        
        Moon updatedMoon = moonRepository.save(moon);
        log.info("Moon updated successfully with ID: {}", updatedMoon.getId());
        
        return mapToResponseDTO(updatedMoon);
    }
    
    @Transactional
    public void deleteMoon(Long id) {
        log.info("Deleting moon with ID: {}", id);
        
        if (!moonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Moon not found with ID: " + id);
        }
        
        moonRepository.deleteById(id);
        log.info("Moon deleted successfully with ID: {}", id);
    }
    
    private MoonResponseDTO mapToResponseDTO(Moon moon) {
        return MoonResponseDTO.builder()
                .id(moon.getId())
                .name(moon.getName())
                .diameterKm(moon.getDiameterKm())
                .orbitalPeriodDays(moon.getOrbitalPeriodDays())
                .planetId(moon.getPlanet().getId())
                .planetName(moon.getPlanet().getName())
                .build();
    }
}

