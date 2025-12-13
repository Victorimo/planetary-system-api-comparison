package ie.spring.planetary.repositories;

import ie.spring.planetary.entities.Moon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoonRepository extends JpaRepository<Moon, Integer> {
    List<Moon> findByPlanet_PlanetId(int planetId);
    
    @Query("SELECT m FROM Moon m WHERE m.planet.name = :planetName")
    List<Moon> findByPlanetName(@Param("planetName") String planetName);
    
    int countByPlanet_PlanetId(int planetId);
}

