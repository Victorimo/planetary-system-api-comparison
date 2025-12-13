package ie.spring.planetary.mappers;

import ie.spring.planetary.dtos.moon.MoonCreateDto;
import ie.spring.planetary.dtos.moon.MoonResponseDto;
import ie.spring.planetary.entities.Moon;
import ie.spring.planetary.entities.Planet;

public class MoonMapper {

    public static MoonResponseDto toResponseDto(Moon moon) {
        String planetName = null;
        if (moon.getPlanet() != null) {
            planetName = moon.getPlanet().getName();
        }
        
        return new MoonResponseDto(
                moon.getMoonId(),
                moon.getName(),
                moon.getDiameterKm(),
                moon.getOrbitalPeriodDays(),
                moon.getPlanet() != null ? moon.getPlanet().getPlanetId() : 0,
                planetName
        );
    }

    public static Moon toEntity(MoonCreateDto dto, Planet planet) {
        Moon moon = new Moon();
        moon.setName(dto.name());
        moon.setDiameterKm(dto.diameterKm());
        moon.setOrbitalPeriodDays(dto.orbitalPeriodDays());
        moon.setPlanet(planet);
        return moon;
    }
}

