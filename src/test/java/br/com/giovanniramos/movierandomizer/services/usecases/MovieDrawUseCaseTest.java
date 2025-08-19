package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.repositories.LastMovieDrawRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static br.com.giovanniramos.movierandomizer.mocks.LastMovieDrawMock.lastMovieDrawEntityMock;
import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieModelMock;
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
class MovieDrawUseCaseTest {
    @Mock
    private GetMovieDetailsUseCase getMovieDetailsUseCase;

    @Mock
    private LastMovieDrawRepository lastMovieDrawRepository;

    @InjectMocks
    private MovieDrawUseCase movieDrawUseCase;

    @Test
    void shouldMovieDrawForTheFirstTimeSuccessfully() {
        final var movieModelMock = movieModelMock();

        when(getMovieDetailsUseCase.execute(anyString())).thenReturn(movieModelMock);
        when(lastMovieDrawRepository.findFirstBy()).thenReturn(Optional.empty());

        final var movieModel = assertDoesNotThrow(() -> movieDrawUseCase.execute(Set.of(UUID.randomUUID().toString())));

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

        verify(lastMovieDrawRepository, times(1)).save(any());
    }

    @Test
    void shouldMovieDrawNotForTheFirstTimeSuccessfully() {
        final var movieModelMock = movieModelMock();

        when(getMovieDetailsUseCase.execute(anyString())).thenReturn(movieModelMock);
        when(lastMovieDrawRepository.findFirstBy()).thenReturn(Optional.of(lastMovieDrawEntityMock()));

        final var movieModel = assertDoesNotThrow(() -> movieDrawUseCase.execute(Set.of(UUID.randomUUID().toString())));

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

        verify(lastMovieDrawRepository, times(1)).save(any());
    }

    @Test
    void shouldTryToMovieDrawAndReturnNotFoundException() {
        final var errorMessage = "Movie not found";
        final var moviesId = Set.of(UUID.randomUUID().toString());

        when(getMovieDetailsUseCase.execute(anyString())).thenThrow(new NotFoundException(errorMessage));

        final var notFoundException = assertThrows(NotFoundException.class, () -> movieDrawUseCase.execute(moviesId));

        assertEquals(404, notFoundException.getCode());
        assertEquals(errorMessage, notFoundException.getMessage());

        verify(lastMovieDrawRepository, never()).save(any());
    }
}