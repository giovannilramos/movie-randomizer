package br.com.giovanniramos.movie_randomizer.handlers.responses;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, int code, LocalDateTime dateTime) {
}
