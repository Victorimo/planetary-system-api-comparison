package ie.spring.planetary.services;

import ie.spring.planetary.dtos.planet.PlanetCreateDto;
import ie.spring.planetary.dtos.planet.PlanetFieldsDto;
import ie.spring.planetary.dtos.planet.PlanetResponseDto;
import ie.spring.planetary.dtos.planet.PlanetUpdateDto;

import java.util.List;

public interface PlanetService {
    PlanetResponseDto createPlanet(PlanetCreateDto createDto);
    List<PlanetResponseDto> getAllPlanets();
    PlanetResponseDto getPlanetById(int id);
    PlanetResponseDto updatePlanet(int id, PlanetUpdateDto updateDto);
    void deletePlanet(int id);
    List<PlanetResponseDto> getPlanetsByType(String type);
    PlanetFieldsDto getPlanetFieldsById(int id);
}

