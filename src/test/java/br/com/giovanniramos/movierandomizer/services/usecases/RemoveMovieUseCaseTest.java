package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import br.com.giovanniramos.movierandomizer.services.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieEntityMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveMovieUseCaseTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private RemoveMovieUseCase removeMovieUseCase;

    @Test
    void shouldRemoveMovieSuccessfully() {
        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movieEntityMock()));

        assertDoesNotThrow(() -> removeMovieUseCase.execute(UUID.randomUUID().toString()));

        verify(movieRepository, times(1)).delete(any());
        verify(minioService, times(1)).removeMinioObject(any());
    }

    @Test
    void shouldTryToRemoveMovieAndReturnNotFoundException() {
        final var id = UUID.randomUUID().toString();

        when(movieRepository.findById(anyString())).thenReturn(Optional.empty());

        final var notFoundException = assertThrows(NotFoundException.class, () -> removeMovieUseCase.execute(id));

        assertEquals(404, notFoundException.getCode());
        assertEquals("Movie not found", notFoundException.getMessage());

        verify(movieRepository, never()).delete(any());
        verify(minioService, never()).removeMinioObject(any());
    }
}