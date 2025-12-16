package com.example.planetarysystem.mapper;

import com.example.planetarysystem.domain.Moon;
import com.example.planetarysystem.dto.moon.MoonResponseDTO;

public final class MoonMapper {
    private MoonMapper() {}

    public static MoonResponseDTO toResponse(Moon entity) {
        return MoonResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .diameterKm(entity.getDiameterKm())
                .orbitalPeriodDays(entity.getOrbitalPeriodDays())
                .planetId(entity.getPlanet().getId())
                .planetName(entity.getPlanet().getName())
                .build();
    }
}
