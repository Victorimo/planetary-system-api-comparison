package com.planetary.controller;

import com.planetary.dto.planet.PlanetRequestDTO;
import com.planetary.dto.planet.PlanetResponseDTO;
import com.planetary.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
@RequiredArgsConstructor
@Tag(name = "Planet Management", description = "APIs for managing planets")
@SecurityRequirement(name = "basicAuth")
public class PlanetController {
    
    private final PlanetService planetService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Create a new planet", description = "Creates a new planet (ADMIN/STAFF only)")
    public ResponseEntity<PlanetResponseDTO> createPlanet(@Valid @RequestBody PlanetRequestDTO requestDTO) {
        PlanetResponseDTO response = planetService.createPlanet(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Get all planets", description = "Retrieves all planets")
    public ResponseEntity<List<PlanetResponseDTO>> getAllPlanets() {
        List<PlanetResponseDTO> planets = planetService.getAllPlanets();
        return ResponseEntity.ok(planets);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Get planet by ID", description = "Retrieves a planet by its ID")
    public ResponseEntity<PlanetResponseDTO> getPlanetById(@PathVariable Long id) {
        PlanetResponseDTO planet = planetService.getPlanetById(id);
        return ResponseEntity.ok(planet);
    }
    
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Get planets by type", description = "Retrieves all planets of a specific type")
    public ResponseEntity<List<PlanetResponseDTO>> getPlanetsByType(@PathVariable String type) {
        List<PlanetResponseDTO> planets = planetService.getPlanetsByType(type);
        return ResponseEntity.ok(planets);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Update a planet", description = "Updates an existing planet (ADMIN/STAFF only)")
    public ResponseEntity<PlanetResponseDTO> updatePlanet(
            @PathVariable Long id,
            @Valid @RequestBody PlanetRequestDTO requestDTO) {
        PlanetResponseDTO response = planetService.updatePlanet(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a planet", description = "Deletes a planet (ADMIN only)")
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        planetService.deletePlanet(id);
        return ResponseEntity.noContent().build();
    }
}

