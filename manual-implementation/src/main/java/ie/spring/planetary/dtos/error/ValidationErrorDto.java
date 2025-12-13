package ie.spring.planetary.dtos.error;

public record ValidationErrorDto(
    String field,
    String message
) {}

