package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.BadRequestException;
import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieEntityMock;
import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateMovieUseCaseTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private UpdateMovieUseCase updateMovieUseCase;

    @Test
    void shouldUpdateMovieSuccessfully() {
        final var movieEntity = movieEntityMock();
        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movieEntity));
        when(genreRepository.existsByName(anyString())).thenReturn(true);
        when(movieRepository.save(any())).thenReturn(movieEntity);

        final var movieModel = assertDoesNotThrow(() -> updateMovieUseCase.execute(movieModelMock()));

        assertNull(movieModel.getMovieCoverUrl());
        assertEquals(movieEntity.getName(), movieModel.getName());
        assertEquals(movieEntity.getGenres().size(), movieModel.getGenres().size());
        assertEquals(movieEntity.getMovieCoverId(), movieModel.getMovieCoverId());
        assertEquals(movieEntity.getMovieType(), movieModel.getMovieType());
        assertEquals(movieEntity.getIsFirstTimeWatching(), movieModel.getIsFirstTimeWatching());
        assertEquals(movieEntity.getAddedBy(), movieModel.getAddedBy());
        assertEquals(movieEntity.getNote(), movieModel.getNote());
        assertEquals(movieEntity.getComments(), movieModel.getComments());
        assertEquals(movieEntity.getDuration(), movieModel.getDuration());
        assertEquals(movieEntity.getCreatedAt(), movieModel.getCreatedAt());
    }

    @Test
    void shouldTryToUpdateMovieAndReturnNotFoundException() {
        final var movieModel = movieModelMock();

        when(movieRepository.findById(anyString())).thenReturn(Optional.empty());

        final var notFoundException = assertThrows(NotFoundException.class, () -> updateMovieUseCase.execute(movieModel));

        assertEquals(404, notFoundException.getCode());
        assertEquals("Movie not found", notFoundException.getMessage());
    }

    @Test
    void shouldTryToUpdateMovieAndReturnBadRequestException() {
        final var movieModel = movieModelMock();

        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movieEntityMock()));
        when(genreRepository.existsByName(anyString())).thenReturn(false);

        final var badRequestException = assertThrows(BadRequestException.class, () -> updateMovieUseCase.execute(movieModel));

        assertEquals(400, badRequestException.getCode());
        assertEquals("One or more invalid genres informed", badRequestException.getMessage());
    }
}