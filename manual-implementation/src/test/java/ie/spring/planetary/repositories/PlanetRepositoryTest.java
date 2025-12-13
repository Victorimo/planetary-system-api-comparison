package ie.spring.planetary.repositories;

import ie.spring.planetary.entities.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=always",
    "spring.sql.init.schema-locations=classpath:schema.sql",
    "spring.sql.init.data-locations=classpath:data.sql"
})
class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    void testFindAll() {
        List<Planet> planets = planetRepository.findAll();
        assertNotNull(planets);
        assertEquals(5, planets.size());
    }

    @Test
    void testFindById() {
        Optional<Planet> planet = planetRepository.findById(1);
        assertTrue(planet.isPresent());
        assertEquals("Earth", planet.get().getName());
    }

    @Test
    void testFindByNameIgnoreCase() {
        Optional<Planet> planet = planetRepository.findByNameIgnoreCase("earth");
        assertTrue(planet.isPresent());
        assertEquals("Earth", planet.get().getName());
    }

    @Test
    void testFindByType() {
        List<Planet> terrestrial = planetRepository.findByTypeIgnoreCase("terrestrial");
        assertNotNull(terrestrial);
        assertEquals(3, terrestrial.size());
    }

    @Test
    void testFindAllByOrderByNameAsc() {
        List<Planet> planets = planetRepository.findAllByOrderByNameAsc();
        assertNotNull(planets);
        assertEquals("Earth", planets.get(0).getName());
    }
}

