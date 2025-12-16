package com.planetary.dto.planet;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanetResponseDTO {
    private Long id;
    private String name;
    private String type;
    private Double radiusKm;
    private Double massKg;
    private Double orbitalPeriodDays;
    private Integer moonCount;
}

