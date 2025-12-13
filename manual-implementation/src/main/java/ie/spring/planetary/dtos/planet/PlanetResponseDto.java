package ie.spring.planetary.dtos.planet;

import ie.spring.planetary.dtos.moon.MoonResponseDto;

import java.util.List;

public record PlanetResponseDto(
    int planetId,
    String name,
    String type,
    double radiusKm,
    double massKg,
    double orbitalPeriodDays,
    List<MoonResponseDto> moons
) {}

