package ie.spring.planetary.dtos.planet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlanetCreateDto(
    @NotBlank(message = "Planet name is required")
    String name,
    
    @NotBlank(message = "Planet type is required")
    String type,
    
    @NotNull(message = "Radius is required")
    @Positive(message = "Radius must be positive")
    Double radiusKm,
    
    @NotNull(message = "Mass is required")
    @Positive(message = "Mass must be positive")
    Double massKg,
    
    @NotNull(message = "Orbital period is required")
    @Positive(message = "Orbital period must be positive")
    Double orbitalPeriodDays
) {}

