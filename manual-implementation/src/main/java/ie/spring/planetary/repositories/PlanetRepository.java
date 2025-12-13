package ie.spring.planetary.repositories;

import ie.spring.planetary.dtos.planet.PlanetFieldsDto;
import ie.spring.planetary.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {
    Optional<Planet> findByNameIgnoreCase(String name);
    List<Planet> findByTypeIgnoreCase(String type);
    List<Planet> findAllByOrderByNameAsc();
    
    @Query("SELECT new ie.spring.planetary.dtos.planet.PlanetFieldsDto(p.name, p.type, p.radiusKm) FROM Planet p")
    List<PlanetFieldsDto> findPlanetFields();
}

