package ie.spring.planetary.services;

import ie.spring.planetary.dtos.moon.MoonCreateDto;
import ie.spring.planetary.dtos.moon.MoonResponseDto;

import java.util.List;

public interface MoonService {
    MoonResponseDto createMoon(MoonCreateDto createDto);
    List<MoonResponseDto> getAllMoons();
    MoonResponseDto getMoonById(int id);
    void deleteMoon(int id);
    List<MoonResponseDto> getMoonsByPlanetName(String planetName);
    int countMoonsByPlanet(String planetName);
}

