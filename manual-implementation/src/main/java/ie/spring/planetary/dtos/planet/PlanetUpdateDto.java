package ie.spring.planetary.dtos.planet;

import jakarta.validation.constraints.Positive;

public record PlanetUpdateDto(
    String type,
    
    @Positive(message = "Radius must be positive")
    Double radiusKm,
    
    @Positive(message = "Mass must be positive")
    Double massKg,
    
    @Positive(message = "Orbital period must be positive")
    Double orbitalPeriodDays
) {}

