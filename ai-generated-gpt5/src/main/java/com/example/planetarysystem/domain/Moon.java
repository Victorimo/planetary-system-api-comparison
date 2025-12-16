package com.example.planetarysystem.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "moons",
    indexes = {
        @Index(name = "idx_moons_name", columnList = "name"),
        @Index(name = "idx_moons_planet_id", columnList = "planet_id")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Moon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "diameter_km", nullable = false)
    private Double diameterKm;

    @Column(name = "orbital_period_days", nullable = false)
    private Double orbitalPeriodDays;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planet_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_moons_planet"))
    private Planet planet;
}
