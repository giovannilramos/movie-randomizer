package br.com.giovanniramos.movierandomizer.handlers.responses;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, int code, LocalDateTime dateTime) {
}
