package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.repositories.MovieRepository;
import br.com.giovanniramos.movie_randomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movie_randomizer.mappers.MovieMapper.MOVIE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetMovieDetailsUseCase {
    private final MovieRepository movieRepository;
    private final MinioService minioService;

    public MovieModel execute(final String id) {
        final var movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movie not found for id: {}", id);
                    return new NotFoundException("Movie not found");
                });

        return MOVIE_MAPPER.mapToMovieModelFromMovieEntity(movieEntity)
                .toBuilder()
                .movieCoverUrl(minioService.getMinioObjectUrl(movieEntity.getMovieCoverId()))
                .build();
    }
}
