package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.entities.MovieEntity;
import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.repositories.MovieRepository;
import br.com.giovanniramos.movie_randomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movie_randomizer.mappers.MovieMapper.MOVIE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowMovieListUseCase {
    private final MovieRepository movieRepository;
    private final MinioService minioService;

    public Page<MovieModel> execute(final MovieModel movieModel, final Pageable pageable) {
        return movieRepository.findAllByFilters(movieModel, pageable)
                .map(this::mapMovieData);
    }

    private MovieModel mapMovieData(final MovieEntity movieEntity) {
        return MOVIE_MAPPER.mapToMovieModelFromMovieEntity(movieEntity).toBuilder()
                .movieCoverUrl(minioService.getMinioObjectUrl(movieEntity.getMovieCoverId()))
                .build();
    }
}
