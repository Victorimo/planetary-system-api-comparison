package com.example.planetarysystem.controller;

import com.example.planetarysystem.dto.moon.MoonRequestDTO;
import com.example.planetarysystem.dto.moon.MoonResponseDTO;
import com.example.planetarysystem.service.MoonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moons")
@RequiredArgsConstructor
public class MoonController {

    private final MoonService moonService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MoonResponseDTO create(@Valid @RequestBody MoonRequestDTO dto) {
        return moonService.create(dto);
    }

    @GetMapping
    public List<MoonResponseDTO> findAll() {
        return moonService.findAll();
    }

    @GetMapping("/{id}")
    public MoonResponseDTO findById(@PathVariable Long id) {
        return moonService.findById(id);
    }

    @GetMapping("/planet/{planetId}")
    public List<MoonResponseDTO> findByPlanet(@PathVariable Long planetId) {
        return moonService.findByPlanetId(planetId);
    }

    @PutMapping("/{id}")
    public MoonResponseDTO update(@PathVariable Long id, @Valid @RequestBody MoonRequestDTO dto) {
        return moonService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        moonService.delete(id);
    }
}
