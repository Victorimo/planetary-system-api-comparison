package com.example.planetarysystem.dto.moon;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MoonRequestDTO {

    @NotBlank
    @Size(min = 1, max = 120)
    private String name;

    @NotNull @Positive
    private Double diameterKm;

    @NotNull @Positive
    private Double orbitalPeriodDays;

    @NotNull
    private Long planetId;
}
