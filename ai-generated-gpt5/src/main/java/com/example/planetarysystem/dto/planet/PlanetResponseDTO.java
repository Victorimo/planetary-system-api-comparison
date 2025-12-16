package com.example.planetarysystem.dto.planet;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PlanetResponseDTO {
    private Long id;
    private String name;
    private String type;
    private Double radiusKm;
    private Double massKg;
    private Double orbitalPeriodDays;
}
