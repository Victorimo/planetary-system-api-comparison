package com.example.planetarysystem.controller;

import com.example.planetarysystem.dto.planet.PlanetRequestDTO;
import com.example.planetarysystem.dto.planet.PlanetResponseDTO;
import com.example.planetarysystem.service.PlanetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanetResponseDTO create(@Valid @RequestBody PlanetRequestDTO dto) {
        return planetService.create(dto);
    }

    @GetMapping
    public List<PlanetResponseDTO> findAll() {
        return planetService.findAll();
    }

    @GetMapping("/{id}")
    public PlanetResponseDTO findById(@PathVariable Long id) {
        return planetService.findById(id);
    }

    @GetMapping("/type/{type}")
    public List<PlanetResponseDTO> findByType(@PathVariable String type) {
        return planetService.findByType(type);
    }

    @PutMapping("/{id}")
    public PlanetResponseDTO update(@PathVariable Long id, @Valid @RequestBody PlanetRequestDTO dto) {
        return planetService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        planetService.delete(id);
    }
}
