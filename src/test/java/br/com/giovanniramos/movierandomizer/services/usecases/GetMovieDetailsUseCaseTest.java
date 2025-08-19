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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMovieDetailsUseCaseTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private GetMovieDetailsUseCase getMovieDetailsUseCase;

    @Test
    void shouldGetMovieDetailsUseCaseSuccessfully() {
        final var movieEntity = movieEntityMock();
        final var movieCoverUrl = "https://test.com";

        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movieEntity));
        when(minioService.getMinioObjectUrl(anyString())).thenReturn(movieCoverUrl);

        final var movieModel = assertDoesNotThrow(() -> getMovieDetailsUseCase.execute(UUID.randomUUID().toString()));

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
    }

    @Test
    void shouldTryToGetMovieDetailsAndReturnNotFoundException() {
        final var id = UUID.randomUUID().toString();

        when(movieRepository.findById(anyString())).thenReturn(Optional.empty());

        final var notFoundException = assertThrows(NotFoundException.class, () -> getMovieDetailsUseCase.execute(id));

        assertEquals(404, notFoundException.getCode());
        assertEquals("Movie not found", notFoundException.getMessage());
    }
}