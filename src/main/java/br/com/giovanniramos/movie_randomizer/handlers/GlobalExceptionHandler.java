package br.com.giovanniramos.movie_randomizer.handlers;

import br.com.giovanniramos.movie_randomizer.exceptions.BaseException;
import br.com.giovanniramos.movie_randomizer.handlers.responses.ExceptionResponse;
import br.com.giovanniramos.movie_randomizer.handlers.responses.ValidatorsExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { BaseException.class })
    public ResponseEntity<ExceptionResponse> baseExceptionHandler(final BaseException baseException) {
        return ResponseEntity
                .status(HttpStatus.valueOf(baseException.getCode()))
                .body(new ExceptionResponse(baseException.getMessage(), baseException.getCode(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ValidatorsExceptionResponse> methodArgumentNotValidExceptionHandler(
            final MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        final var errors = new HashMap<String, List<String>>();
        methodArgumentNotValidException.getAllErrors().forEach(error -> {
            final var field = ((FieldError) error).getField();
            final var errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(field, _ -> new ArrayList<>()).add(errorMessage);
        });
        return ResponseEntity
                .badRequest()
                .body(new ValidatorsExceptionResponse("Bad Request",
                        errors,
                        LocalDateTime.now()));
    }
}
