package com.planetary.dto.moon;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoonResponseDTO {
    private Long id;
    private String name;
    private Double diameterKm;
    private Double orbitalPeriodDays;
    private Long planetId;
    private String planetName;
}

