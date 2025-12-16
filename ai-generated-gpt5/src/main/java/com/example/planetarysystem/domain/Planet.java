package com.example.planetarysystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "planets",
    indexes = {
        @Index(name = "idx_planets_name", columnList = "name", unique = true),
        @Index(name = "idx_planets_type", columnList = "type")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(nullable = false, length = 80)
    private String type;

    @Column(name = "radius_km", nullable = false)
    private Double radiusKm;

    @Column(name = "mass_kg", nullable = false)
    private Double massKg;

    @Column(name = "orbital_period_days", nullable = false)
    private Double orbitalPeriodDays;

    @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Moon> moons = new ArrayList<>();
}
