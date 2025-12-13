package ie.spring.planetary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "planets")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planetId;

    @Column(nullable = false, unique = true)
    private String name;

    private String type;
    private double radiusKm;
    private double massKg;
    private double orbitalPeriodDays;

    @OneToMany(mappedBy = "planet", fetch = FetchType.LAZY)
    private List<Moon> moons;
}

