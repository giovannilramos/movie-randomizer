package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.BadRequestException;
import br.com.giovanniramos.movierandomizer.exceptions.ConflictException;
import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import br.com.giovanniramos.movierandomizer.services.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieEntityMock;
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
class AddMovieUseCaseTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private AddMovieUseCase addMovieUseCase;

    @Test
    void shouldAddMovieSuccessfully() {
        final var movieEntity = movieEntityMock();
        final var movieCoverUrl = "https://test.com";

        when(genreRepository.existsByName(anyString())).thenReturn(true);
        when(movieRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(movieRepository.save(any())).thenReturn(movieEntity);
        when(minioService.getMinioObjectUrl(any())).thenReturn(movieCoverUrl);

        final var movieModel = assertDoesNotThrow(() -> addMovieUseCase.execute(movieModelMock()));

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
        assertEquals(movieCoverUrl, movieModel.getMovieCoverUrl());

        verify(minioService, times(1)).putMinioObject(any(), anyString());
    }

    @Test
    void shouldTryToAddMovieAndThrowBadRequestException() {
        final var movieModel = movieModelMock();

        when(genreRepository.existsByName(anyString())).thenReturn(false);

        final var badRequestException = assertThrows(BadRequestException.class, () -> addMovieUseCase.execute(movieModel));

        assertEquals(400, badRequestException.getCode());
        assertEquals("One or more invalids genres informed", badRequestException.getMessage());

        verify(minioService, never()).putMinioObject(any(), anyString());
    }

    @Test
    void shouldTryToAddMovieAndThrowConflictException() {
        final var movieModel = movieModelMock();

        when(genreRepository.existsByName(anyString())).thenReturn(true);
        when(movieRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);

        final var conflictException = assertThrows(ConflictException.class, () -> addMovieUseCase.execute(movieModel));

        assertEquals(409, conflictException.getCode());
        assertEquals("Movie already exists", conflictException.getMessage());

        verify(minioService, never()).putMinioObject(any(), anyString());
    }
}