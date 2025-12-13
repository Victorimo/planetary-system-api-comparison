package ie.spring.planetary.dtos.moon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MoonCreateDto(
    @NotBlank(message = "Moon name is required")
    String name,
    
    @NotNull(message = "Diameter is required")
    @Positive(message = "Diameter must be positive")
    Double diameterKm,
    
    @NotNull(message = "Orbital period is required")
    @Positive(message = "Orbital period must be positive")
    Double orbitalPeriodDays,
    
    @NotNull(message = "Planet ID is required")
    @Positive(message = "Planet ID must be positive")
    Integer planetId
) {}

