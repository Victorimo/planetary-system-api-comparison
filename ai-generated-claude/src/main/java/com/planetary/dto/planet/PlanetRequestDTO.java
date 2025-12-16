package com.planetary.dto.planet;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanetRequestDTO {
    
    @NotBlank(message = "Planet name is required")
    @Size(min = 2, max = 100, message = "Planet name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Planet type is required")
    @Size(min = 2, max = 50, message = "Planet type must be between 2 and 50 characters")
    private String type;
    
    @NotNull(message = "Radius is required")
    @Min(value = 0, message = "Radius must be positive")
    private Double radiusKm;
    
    @NotNull(message = "Mass is required")
    @Min(value = 0, message = "Mass must be positive")
    private Double massKg;
    
    @NotNull(message = "Orbital period is required")
    @Min(value = 0, message = "Orbital period must be positive")
    private Double orbitalPeriodDays;
}

