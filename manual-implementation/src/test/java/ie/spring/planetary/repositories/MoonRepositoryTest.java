package ie.spring.planetary.repositories;

import ie.spring.planetary.entities.Moon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=always",
    "spring.sql.init.schema-locations=classpath:schema.sql",
    "spring.sql.init.data-locations=classpath:data.sql"
})
class MoonRepositoryTest {

    @Autowired
    private MoonRepository moonRepository;

    @Test
    void testFindAll() {
        List<Moon> moons = moonRepository.findAll();
        assertNotNull(moons);
        assertEquals(9, moons.size());
    }

    @Test
    void testFindByPlanetId() {
        List<Moon> moons = moonRepository.findByPlanet_PlanetId(1);
        assertNotNull(moons);
        assertEquals(1, moons.size());
        assertEquals("Moon", moons.get(0).getName());
    }

    @Test
    void testFindByPlanetName() {
        List<Moon> moons = moonRepository.findByPlanetName("Jupiter");
        assertNotNull(moons);
        assertEquals(4, moons.size());
    }

    @Test
    void testCountByPlanetId() {
        int count = moonRepository.countByPlanet_PlanetId(3);
        assertEquals(4, count);
    }
}

