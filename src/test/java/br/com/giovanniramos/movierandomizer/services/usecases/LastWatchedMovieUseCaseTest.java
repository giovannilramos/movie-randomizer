package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.repositories.LastMovieDrawRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.giovanniramos.movierandomizer.mocks.LastMovieDrawMock.lastMovieDrawEntityMock;
import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LastWatchedMovieUseCaseTest {
    @Mock
    private LastMovieDrawRepository lastMovieDrawRepository;

    @Mock
    private GetMovieDetailsUseCase getMovieDetailsUseCase;

    @InjectMocks
    private LastWatchedMovieUseCase lastWatchedMovieUseCase;

    @Test
    void shouldReturnLastWatchedMovieSuccessfully() {
        final var movieModelMock = movieModelMock();

        when(lastMovieDrawRepository.findFirstBy()).thenReturn(Optional.of(lastMovieDrawEntityMock()));
        when(getMovieDetailsUseCase.execute(anyString())).thenReturn(movieModelMock);

        final var movieModel = assertDoesNotThrow(() -> lastWatchedMovieUseCase.execute());

        assertEquals(movieModelMock.getName(), movieModel.getName());
        assertEquals(movieModelMock.getGenres().size(), movieModel.getGenres().size());
        assertEquals(movieModelMock.getMovieCoverId(), movieModel.getMovieCoverId());
        assertEquals(movieModelMock.getMovieType(), movieModel.getMovieType());
        assertEquals(movieModelMock.getIsFirstTimeWatching(), movieModel.getIsFirstTimeWatching());
        assertEquals(movieModelMock.getAddedBy(), movieModel.getAddedBy());
        assertEquals(movieModelMock.getNote(), movieModel.getNote());
        assertEquals(movieModelMock.getComments(), movieModel.getComments());
        assertEquals(movieModelMock.getDuration(), movieModel.getDuration());
        assertEquals(movieModelMock.getCreatedAt(), movieModel.getCreatedAt());
        assertEquals(movieModelMock.getMovieCoverUrl(), movieModel.getMovieCoverUrl());
    }

    @Test
    void shouldTryToFindLastWatchedMovieAndReturnNotFoundExceptionWhenLastMovieDrawNotFound() {
        final var errorMessage = "Movie not found";

        when(lastMovieDrawRepository.findFirstBy()).thenReturn(Optional.of(lastMovieDrawEntityMock()));
        when(getMovieDetailsUseCase.execute(anyString())).thenThrow(new NotFoundException(errorMessage));

        final var notFoundException = assertThrows(NotFoundException.class, () -> lastWatchedMovieUseCase.execute());

        assertEquals(404, notFoundException.getCode());
        assertEquals(errorMessage, notFoundException.getMessage());
    }

    @Test
    void shouldTryToFindLastWatchedMovieAndReturnNotFoundExceptionWhenMovieEntityNotFound() {
        when(lastMovieDrawRepository.findFirstBy()).thenReturn(Optional.empty());

        final var notFoundException = assertThrows(NotFoundException.class, () -> lastWatchedMovieUseCase.execute());

        assertEquals(404, notFoundException.getCode());
        assertEquals("No movies watched yet", notFoundException.getMessage());
    }
}