package com.example.planetarysystem.dto.moon;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MoonResponseDTO {
    private Long id;
    private String name;
    private Double diameterKm;
    private Double orbitalPeriodDays;

    private Long planetId;
    private String planetName;
}
