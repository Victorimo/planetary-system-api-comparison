package com.example.planetarysystem.service;

import com.example.planetarysystem.domain.Planet;
import com.example.planetarysystem.dto.planet.PlanetRequestDTO;
import com.example.planetarysystem.dto.planet.PlanetResponseDTO;
import com.example.planetarysystem.exception.BadRequestException;
import com.example.planetarysystem.exception.ResourceNotFoundException;
import com.example.planetarysystem.mapper.PlanetMapper;
import com.example.planetarysystem.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanetService {

    private final PlanetRepository planetRepository;

    @Transactional
    public PlanetResponseDTO create(PlanetRequestDTO dto) {
        planetRepository.findByNameIgnoreCase(dto.getName())
                .ifPresent(p -> { throw new BadRequestException("Planet with name '" + dto.getName() + "' already exists"); });

        Planet entity = Planet.builder().build();
        PlanetMapper.applyRequest(entity, dto);

        Planet saved = planetRepository.save(entity);
        return PlanetMapper.toResponse(saved);
    }

    public List<PlanetResponseDTO> findAll() {
        return planetRepository.findAll().stream()
                .map(PlanetMapper::toResponse)
                .toList();
    }

    public PlanetResponseDTO findById(Long id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found: id=" + id));
        return PlanetMapper.toResponse(planet);
    }

    public List<PlanetResponseDTO> findByType(String type) {
        return planetRepository.findByTypeIgnoreCase(type).stream()
                .map(PlanetMapper::toResponse)
                .toList();
    }

    @Transactional
    public PlanetResponseDTO update(Long id, PlanetRequestDTO dto) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found: id=" + id));

        planetRepository.findByNameIgnoreCase(dto.getName())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(p -> { throw new BadRequestException("Planet with name '" + dto.getName() + "' already exists"); });

        PlanetMapper.applyRequest(planet, dto);
        Planet saved = planetRepository.save(planet);
        return PlanetMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found: id=" + id));
        planetRepository.delete(planet);
    }

    // Internal use for MoonService
    public Planet getEntityOrThrow(Long id) {
        return planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found: id=" + id));
    }
}
