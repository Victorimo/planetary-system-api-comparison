package ie.spring.planetary.services;

import ie.spring.planetary.dtos.planet.PlanetCreateDto;
import ie.spring.planetary.dtos.planet.PlanetFieldsDto;
import ie.spring.planetary.dtos.planet.PlanetResponseDto;
import ie.spring.planetary.dtos.planet.PlanetUpdateDto;
import ie.spring.planetary.entities.Planet;
import ie.spring.planetary.exceptions.DuplicateEntityException;
import ie.spring.planetary.exceptions.NotFoundException;
import ie.spring.planetary.mappers.PlanetMapper;
import ie.spring.planetary.repositories.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;

    @Override
    public PlanetResponseDto createPlanet(PlanetCreateDto createDto) {
        // Check if planet with same name already exists
        if (planetRepository.findByNameIgnoreCase(createDto.name()).isPresent()) {
            throw new DuplicateEntityException("Planet with name '" + createDto.name() + "' already exists");
        }

        Planet planet = PlanetMapper.toEntity(createDto);
        Planet savedPlanet = planetRepository.save(planet);
        return PlanetMapper.toResponseDto(savedPlanet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanetResponseDto> getAllPlanets() {
        List<Planet> planets = planetRepository.findAllByOrderByNameAsc();
        return planets.stream()
                .map(PlanetMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PlanetResponseDto getPlanetById(int id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Planet with ID " + id + " not found"));
        return PlanetMapper.toResponseDto(planet);
    }

    @Override
    public PlanetResponseDto updatePlanet(int id, PlanetUpdateDto updateDto) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Planet with ID " + id + " not found"));

        PlanetMapper.updateEntityFromDto(planet, updateDto);
        Planet updatedPlanet = planetRepository.save(planet);
        return PlanetMapper.toResponseDto(updatedPlanet);
    }

    @Override
    public void deletePlanet(int id) {
        if (!planetRepository.existsById(id)) {
            throw new NotFoundException("Planet with ID " + id + " not found");
        }
        planetRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanetResponseDto> getPlanetsByType(String type) {
        List<Planet> planets = planetRepository.findByTypeIgnoreCase(type);
        return planets.stream()
                .map(PlanetMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PlanetFieldsDto getPlanetFieldsById(int id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Planet with ID " + id + " not found"));
        return new PlanetFieldsDto(
                planet.getName(),
                planet.getType(),
                planet.getRadiusKm()
        );
    }
}

