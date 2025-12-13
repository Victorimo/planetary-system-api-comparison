package ie.spring.planetary.controllers;

import ie.spring.planetary.dtos.moon.MoonCreateDto;
import ie.spring.planetary.dtos.moon.MoonResponseDto;
import ie.spring.planetary.services.MoonService;
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
@RequestMapping("/api/moons")
@Tag(name = "Moons", description = "Moon management API")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class MoonController {

    private final MoonService moonService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Add a new moon")
    public MoonResponseDto createMoon(@Valid @RequestBody MoonCreateDto createDto) {
        return moonService.createMoon(createDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Retrieve all moons")
    public List<MoonResponseDto> getAllMoons() {
        return moonService.getAllMoons();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Retrieve a moon by ID")
    public MoonResponseDto getMoonById(@PathVariable int id) {
        return moonService.getMoonById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Remove a moon")
    public void deleteMoon(@PathVariable int id) {
        moonService.deleteMoon(id);
    }

    @GetMapping("/planet/{planetName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "List moons by planet name")
    public List<MoonResponseDto> getMoonsByPlanetName(@PathVariable String planetName) {
        return moonService.getMoonsByPlanetName(planetName);
    }

    @GetMapping("/planet/{planetName}/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'STUDENT')")
    @Operation(summary = "Count moons by planet name")
    public int countMoonsByPlanet(@PathVariable String planetName) {
        return moonService.countMoonsByPlanet(planetName);
    }
}

