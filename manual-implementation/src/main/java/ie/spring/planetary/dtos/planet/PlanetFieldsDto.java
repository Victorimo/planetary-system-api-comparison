package ie.spring.planetary.dtos.planet;

public record PlanetFieldsDto(
    String name,
    String type,
    double radiusKm
) {}

