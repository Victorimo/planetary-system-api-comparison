package com.example.planetarysystem.dto.planet;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PlanetRequestDTO {

    @NotBlank
    @Size(min = 1, max = 120)
    private String name;

    @NotBlank
    @Size(min = 1, max = 80)
    private String type;

    @NotNull @Positive
    private Double radiusKm;

    @NotNull @Positive
    private Double massKg;

    @NotNull
    @Min(1)
    private Double orbitalPeriodDays;
}
