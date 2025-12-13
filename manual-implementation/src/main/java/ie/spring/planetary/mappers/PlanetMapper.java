package ie.spring.planetary.mappers;

import ie.spring.planetary.dtos.moon.MoonResponseDto;
import ie.spring.planetary.dtos.planet.PlanetCreateDto;
import ie.spring.planetary.dtos.planet.PlanetResponseDto;
import ie.spring.planetary.dtos.planet.PlanetUpdateDto;
import ie.spring.planetary.entities.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetMapper {

    public static PlanetResponseDto toResponseDto(Planet planet) {
        List<MoonResponseDto> moonDtos = null;
        if (planet.getMoons() != null && !planet.getMoons().isEmpty()) {
            moonDtos = planet.getMoons().stream()
                    .map(MoonMapper::toResponseDto)
                    .collect(Collectors.toList());
        }
        
        return new PlanetResponseDto(
                planet.getPlanetId(),
                planet.getName(),
                planet.getType(),
                planet.getRadiusKm(),
                planet.getMassKg(),
                planet.getOrbitalPeriodDays(),
                moonDtos
        );
    }

    public static Planet toEntity(PlanetCreateDto dto) {
        Planet planet = new Planet();
        planet.setName(dto.name());
        planet.setType(dto.type());
        planet.setRadiusKm(dto.radiusKm());
        planet.setMassKg(dto.massKg());
        planet.setOrbitalPeriodDays(dto.orbitalPeriodDays());
        return planet;
    }

    public static void updateEntityFromDto(Planet planet, PlanetUpdateDto dto) {
        if (dto.type() != null) {
            planet.setType(dto.type());
        }
        if (dto.radiusKm() != null) {
            planet.setRadiusKm(dto.radiusKm());
        }
        if (dto.massKg() != null) {
            planet.setMassKg(dto.massKg());
        }
        if (dto.orbitalPeriodDays() != null) {
            planet.setOrbitalPeriodDays(dto.orbitalPeriodDays());
        }
    }
}

