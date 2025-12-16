package com.example.planetarysystem.service;

import com.example.planetarysystem.domain.Moon;
import com.example.planetarysystem.domain.Planet;
import com.example.planetarysystem.dto.moon.MoonRequestDTO;
import com.example.planetarysystem.dto.moon.MoonResponseDTO;
import com.example.planetarysystem.exception.ResourceNotFoundException;
import com.example.planetarysystem.mapper.MoonMapper;
import com.example.planetarysystem.repository.MoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoonService {

    private final MoonRepository moonRepository;
    private final PlanetService planetService;

    @Transactional
    public MoonResponseDTO create(MoonRequestDTO dto) {
        Planet planet = planetService.getEntityOrThrow(dto.getPlanetId());

        Moon moon = Moon.builder()
                .name(dto.getName())
                .diameterKm(dto.getDiameterKm())
                .orbitalPeriodDays(dto.getOrbitalPeriodDays())
                .planet(planet)
                .build();

        Moon saved = moonRepository.save(moon);
        return MoonMapper.toResponse(saved);
    }

    public List<MoonResponseDTO> findAll() {
        return moonRepository.findAll().stream()
                .map(MoonMapper::toResponse)
                .toList();
    }

    public MoonResponseDTO findById(Long id) {
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found: id=" + id));
        return MoonMapper.toResponse(moon);
    }

    public List<MoonResponseDTO> findByPlanetId(Long planetId) {
        // ensure planet exists for clearer 404
        planetService.getEntityOrThrow(planetId);

        return moonRepository.findByPlanetId(planetId).stream()
                .map(MoonMapper::toResponse)
                .toList();
    }

    @Transactional
    public MoonResponseDTO update(Long id, MoonRequestDTO dto) {
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found: id=" + id));

        Planet planet = planetService.getEntityOrThrow(dto.getPlanetId());

        moon.setName(dto.getName());
        moon.setDiameterKm(dto.getDiameterKm());
        moon.setOrbitalPeriodDays(dto.getOrbitalPeriodDays());
        moon.setPlanet(planet);

        Moon saved = moonRepository.save(moon);
        return MoonMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found: id=" + id));
        moonRepository.delete(moon);
    }
}
