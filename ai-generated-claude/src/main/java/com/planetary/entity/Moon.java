package com.planetary.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "moons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "diameter_km", nullable = false)
    private Double diameterKm;
    
    @Column(name = "orbital_period_days", nullable = false)
    private Double orbitalPeriodDays;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planet_id", nullable = false)
    private Planet planet;
}

