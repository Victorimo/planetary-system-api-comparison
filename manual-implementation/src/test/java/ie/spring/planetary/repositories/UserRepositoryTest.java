package ie.spring.planetary.repositories;

import ie.spring.planetary.entities.User;
import ie.spring.planetary.enums.Role;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    void testFindByUsernameIgnoreCase() {
        Optional<User> user = userRepository.findByUsernameIgnoreCase("ADMIN");
        assertTrue(user.isPresent());
        assertEquals("admin", user.get().getUsername());
        assertEquals(Role.ADMIN, user.get().getRole());
    }

    @Test
    void testExistsByUsername() {
        assertTrue(userRepository.existsByUsernameIgnoreCase("admin"));
        assertFalse(userRepository.existsByUsernameIgnoreCase("nonexistent"));
    }

    @Test
    void testFindByRole() {
        List<User> admins = userRepository.findByRole(Role.ADMIN);
        assertNotNull(admins);
        assertEquals(1, admins.size());
    }

    @Test
    void testFindByEnabledTrue() {
        List<User> enabledUsers = userRepository.findByEnabledTrue();
        assertNotNull(enabledUsers);
        assertEquals(3, enabledUsers.size());
    }
}

