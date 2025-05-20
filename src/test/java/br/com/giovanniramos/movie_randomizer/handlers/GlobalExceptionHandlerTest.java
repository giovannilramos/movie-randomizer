package br.com.giovanniramos.movie_randomizer.handlers;

import br.com.giovanniramos.movie_randomizer.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Test
    void shouldHandleBaseException() {
        final var notFoundException = new NotFoundException("Not Found");

        final var exceptionHandledResponse = new GlobalExceptionHandler()
                .baseExceptionHandler(notFoundException);

        assertNotNull(exceptionHandledResponse.getBody());
        assertEquals(notFoundException.getCode(), exceptionHandledResponse.getStatusCode().value());
        assertEquals(notFoundException.getMessage(), exceptionHandledResponse.getBody().message());
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        final var field = "username";
        final var defaultMessage = "username is required";
        final var fieldErrors = List.of(new FieldError("objectName", field, defaultMessage));

        when(methodArgumentNotValidException.getAllErrors()).thenReturn(List.copyOf(fieldErrors));

        final var exceptionHandledResponse = new GlobalExceptionHandler()
                .methodArgumentNotValidExceptionHandler(methodArgumentNotValidException);

        assertEquals(400, exceptionHandledResponse.getStatusCode().value());
        assertNotNull(exceptionHandledResponse.getBody());

        final var errors = exceptionHandledResponse.getBody().errors();

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey(field));
        assertEquals(List.of(defaultMessage), errors.get(field));
    }
}