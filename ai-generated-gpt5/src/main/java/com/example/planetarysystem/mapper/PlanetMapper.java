package com.example.planetarysystem.mapper;

import com.example.planetarysystem.domain.Planet;
import com.example.planetarysystem.dto.planet.PlanetRequestDTO;
import com.example.planetarysystem.dto.planet.PlanetResponseDTO;

public final class PlanetMapper {
    private PlanetMapper() {}

    public static PlanetResponseDTO toResponse(Planet entity) {
        return PlanetResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .radiusKm(entity.getRadiusKm())
                .massKg(entity.getMassKg())
                .orbitalPeriodDays(entity.getOrbitalPeriodDays())
                .build();
    }

    public static void applyRequest(Planet entity, PlanetRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setRadiusKm(dto.getRadiusKm());
        entity.setMassKg(dto.getMassKg());
        entity.setOrbitalPeriodDays(dto.getOrbitalPeriodDays());
    }
}
