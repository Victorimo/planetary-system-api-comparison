-- Sample Planets
INSERT INTO planets (name, type, radius_km, mass_kg, orbital_period_days) VALUES
('Earth', 'terrestrial', 6371.0, 5.972e24, 365.25),
('Mars', 'terrestrial', 3389.5, 6.39e23, 686.98),
('Jupiter', 'gas giant', 69911.0, 1.898e27, 4332.59),
('Saturn', 'gas giant', 58232.0, 5.683e26, 10759.22),
('Venus', 'terrestrial', 6051.8, 4.867e24, 224.7);

-- Sample Moons
INSERT INTO moons (name, diameter_km, orbital_period_days, planet_id) VALUES
('Moon', 3474.8, 27.32, 1),
('Phobos', 22.2, 0.32, 2),
('Deimos', 12.4, 1.26, 2),
('Io', 3643.2, 1.77, 3),
('Europa', 3121.6, 3.55, 3),
('Ganymede', 5268.2, 7.15, 3),
('Callisto', 4820.6, 16.69, 3),
('Titan', 5150.0, 15.95, 4),
('Enceladus', 504.2, 1.37, 4);

-- Sample Users (password: "password123" - BCrypt hashed)
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$eHCqEP2tlwuJOc/6UsRmIe/Jv6X3wxc6i/PjFuLG0IyKjYBGYGZX6', 'ADMIN'),
('staff1', '$2a$10$eHCqEP2tlwuJOc/6UsRmIe/Jv6X3wxc6i/PjFuLG0IyKjYBGYGZX6', 'STAFF'),
('student1', '$2a$10$eHCqEP2tlwuJOc/6UsRmIe/Jv6X3wxc6i/PjFuLG0IyKjYBGYGZX6', 'STUDENT');

