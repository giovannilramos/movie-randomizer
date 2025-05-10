package br.com.giovanniramos.movie_randomizer.handlers.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ValidatorsExceptionResponse(String message, Map<String, List<String>> errors, LocalDateTime dateTime) {
}
