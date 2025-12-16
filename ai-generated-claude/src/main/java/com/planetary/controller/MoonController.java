package com.planetary.controller;

import com.planetary.dto.moon.MoonRequestDTO;
import com.planetary.dto.moon.MoonResponseDTO;
import com.planetary.service.MoonService;
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
@RequestMapping("/api/moons")
@RequiredArgsConstructor
@Tag(name = "Moon Management", description = "APIs for managing moons")
@SecurityRequirement(name = "basicAuth")
public class MoonController {
    
    private final MoonService moonService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Create a new moon", description = "Creates a new moon (ADMIN/STAFF only)")
    public ResponseEntity<MoonResponseDTO> createMoon(@Valid @RequestBody MoonRequestDTO requestDTO) {
        MoonResponseDTO response = moonService.createMoon(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Get all moons", description = "Retrieves all moons")
    public ResponseEntity<List<MoonResponseDTO>> getAllMoons() {
        List<MoonResponseDTO> moons = moonService.getAllMoons();
        return ResponseEntity.ok(moons);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Get moon by ID", description = "Retrieves a moon by its ID")
    public ResponseEntity<MoonResponseDTO> getMoonById(@PathVariable Long id) {
        MoonResponseDTO moon = moonService.getMoonById(id);
        return ResponseEntity.ok(moon);
    }
    
    @GetMapping("/planet/{planetId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Get moons by planet ID", description = "Retrieves all moons for a specific planet")
    public ResponseEntity<List<MoonResponseDTO>> getMoonsByPlanetId(@PathVariable Long planetId) {
        List<MoonResponseDTO> moons = moonService.getMoonsByPlanetId(planetId);
        return ResponseEntity.ok(moons);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Update a moon", description = "Updates an existing moon (ADMIN/STAFF only)")
    public ResponseEntity<MoonResponseDTO> updateMoon(
            @PathVariable Long id,
            @Valid @RequestBody MoonRequestDTO requestDTO) {
        MoonResponseDTO response = moonService.updateMoon(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a moon", description = "Deletes a moon (ADMIN only)")
    public ResponseEntity<Void> deleteMoon(@PathVariable Long id) {
        moonService.deleteMoon(id);
        return ResponseEntity.noContent().build();
    }
}

