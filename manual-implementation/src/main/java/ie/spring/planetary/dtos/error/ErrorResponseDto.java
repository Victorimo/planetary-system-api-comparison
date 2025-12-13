package ie.spring.planetary.dtos.error;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDto(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    List<ValidationErrorDto> validationErrors
) {
    public ErrorResponseDto(LocalDateTime timestamp, int status, String error, String message) {
        this(timestamp, status, error, message, null);
    }
}

