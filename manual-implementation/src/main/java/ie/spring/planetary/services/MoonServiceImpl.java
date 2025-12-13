package ie.spring.planetary.services;

import ie.spring.planetary.dtos.moon.MoonCreateDto;
import ie.spring.planetary.dtos.moon.MoonResponseDto;
import ie.spring.planetary.entities.Moon;
import ie.spring.planetary.entities.Planet;
import ie.spring.planetary.exceptions.NotFoundException;
import ie.spring.planetary.mappers.MoonMapper;
import ie.spring.planetary.repositories.MoonRepository;
import ie.spring.planetary.repositories.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MoonServiceImpl implements MoonService {

    private final MoonRepository moonRepository;
    private final PlanetRepository planetRepository;

    @Override
    public MoonResponseDto createMoon(MoonCreateDto createDto) {
        Planet planet = planetRepository.findById(createDto.planetId())
                .orElseThrow(() -> new NotFoundException("Planet with ID " + createDto.planetId() + " not found"));

        Moon moon = MoonMapper.toEntity(createDto, planet);
        Moon savedMoon = moonRepository.save(moon);
        return MoonMapper.toResponseDto(savedMoon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MoonResponseDto> getAllMoons() {
        List<Moon> moons = moonRepository.findAll();
        return moons.stream()
                .map(MoonMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MoonResponseDto getMoonById(int id) {
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moon with ID " + id + " not found"));
        return MoonMapper.toResponseDto(moon);
    }

    @Override
    public void deleteMoon(int id) {
        if (!moonRepository.existsById(id)) {
            throw new NotFoundException("Moon with ID " + id + " not found");
        }
        moonRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MoonResponseDto> getMoonsByPlanetName(String planetName) {
        List<Moon> moons = moonRepository.findByPlanetName(planetName);
        return moons.stream()
                .map(MoonMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int countMoonsByPlanet(String planetName) {
        Planet planet = planetRepository.findByNameIgnoreCase(planetName)
                .orElseThrow(() -> new NotFoundException("Planet with name '" + planetName + "' not found"));
        return moonRepository.countByPlanet_PlanetId(planet.getPlanetId());
    }
}

