package ie.spring.planetary.dtos.moon;

public record MoonResponseDto(
    int moonId,
    String name,
    double diameterKm,
    double orbitalPeriodDays,
    int planetId,
    String planetName
) {}

