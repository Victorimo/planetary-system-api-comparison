package ie.spring.planetary.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "moons")
public class Moon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moonId;

    private String name;
    private double diameterKm;
    private double orbitalPeriodDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planet_id")
    private Planet planet;
}

