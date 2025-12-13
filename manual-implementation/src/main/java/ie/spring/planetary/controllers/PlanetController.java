package ie.spring.planetary.controllers;

import ie.spring.planetary.dtos.planet.PlanetCreateDto;
import ie.spring.planetary.dtos.planet.PlanetFieldsDto;
import ie.spring.planetary.dtos.planet.PlanetResponseDto;
import ie.spring.planetary.dtos.planet.PlanetUpdateDto;
import ie.spring.planetary.services.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
@Tag(name = "Planets", description = "Planet management API")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Add a new planet")
    public PlanetResponseDto createPlanet(@Valid @RequestBody PlanetCreateDto createDto) {
        return planetService.createPlanet(createDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Retrieve all planets")
    public List<PlanetResponseDto> getAllPlanets() {
        return planetService.getAllPlanets();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Retrieve a planet by ID")
    public PlanetResponseDto getPlanetById(@PathVariable int id) {
        return planetService.getPlanetById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Update planet details")
    public PlanetResponseDto updatePlanet(@PathVariable int id, @Valid @RequestBody PlanetUpdateDto updateDto) {
        return planetService.updatePlanet(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Remove a planet")
    public void deletePlanet(@PathVariable int id) {
        planetService.deletePlanet(id);
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Retrieve planets by type")
    public List<PlanetResponseDto> getPlanetsByType(@PathVariable String type) {
        return planetService.getPlanetsByType(type);
    }

    @GetMapping("/{id}/fields")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Retrieve specific planet fields")
    public PlanetFieldsDto getPlanetFields(@PathVariable int id) {
        return planetService.getPlanetFieldsById(id);
    }
}

