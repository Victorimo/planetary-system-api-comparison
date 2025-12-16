package com.planetary.dto.moon;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoonRequestDTO {
    
    @NotBlank(message = "Moon name is required")
    @Size(min = 2, max = 100, message = "Moon name must be between 2 and 100 characters")
    private String name;
    
    @NotNull(message = "Diameter is required")
    @Min(value = 0, message = "Diameter must be positive")
    private Double diameterKm;
    
    @NotNull(message = "Orbital period is required")
    @Min(value = 0, message = "Orbital period must be positive")
    private Double orbitalPeriodDays;
    
    @NotNull(message = "Planet ID is required")
    private Long planetId;
}

