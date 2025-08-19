package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import br.com.giovanniramos.movierandomizer.services.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieEntityMock;
import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowMovieListUseCaseTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private ShowMovieListUseCase showMovieListUseCase;

    @Test
    void shouldShowMovieListSuccessfully() {
        final var movieEntity = movieEntityMock();
        final var pageable = PageRequest.of(0, 10);
        final var movieCoverUrl = "https://test.com";

        when(movieRepository.findAllByFilters(any(), any())).thenReturn(new PageImpl<>(List.of(movieEntity), pageable, 1));
        when(minioService.getMinioObjectUrl(any())).thenReturn(movieCoverUrl);

        final var movieModels = assertDoesNotThrow(() -> showMovieListUseCase.execute(movieModelMock(), pageable));
        final var movieModel = movieModels.getContent().getFirst();

        assertEquals(1, movieModels.getTotalElements());
        assertEquals(0, movieModels.getPageable().getPageNumber());
        assertEquals(10, movieModels.getPageable().getPageSize());
        assertEquals(1, movieModels.getTotalPages());
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
}