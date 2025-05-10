package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.entities.LastMovieDrawEntity;
import br.com.giovanniramos.movie_randomizer.entities.MovieEntity;
import br.com.giovanniramos.movie_randomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.repositories.LastMovieDrawRepository;
import br.com.giovanniramos.movie_randomizer.repositories.MovieRepository;
import br.com.giovanniramos.movie_randomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import static br.com.giovanniramos.movie_randomizer.mappers.MovieMapper.MOVIE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieDrawUseCase {
    private static final Random RANDOM = new Random();

    private final MovieRepository movieRepository;
    private final LastMovieDrawRepository lastMovieDrawRepository;
    private final MinioService minioService;

    public MovieModel execute(final Set<String> moviesId) {
        final var randomIndex = RANDOM.nextInt(moviesId.size());
        final var movieId = new ArrayList<>(moviesId).get(randomIndex);
        final var movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> {
                    log.error("Invalid movie id informed. id: {}", movieId);
                    return new NotFoundException("Movie not found");
                });
        saveLastMovieDraw(movieEntity);

        return MOVIE_MAPPER.mapToMovieModelFromMovieEntity(movieEntity)
                .toBuilder().movieCoverUrl(minioService.getMinioObjectUrl(movieEntity.getMovieCoverId())).build();
    }

    private void saveLastMovieDraw(final MovieEntity movieEntity) {
        lastMovieDrawRepository.findFirstBy().ifPresentOrElse(lastMovieDrawEntity ->
                        lastMovieDrawRepository.save(lastMovieDrawEntity.toBuilder()
                                .lastDrawMovieId(movieEntity.getId())
                                .build()),
                () -> lastMovieDrawRepository.save(LastMovieDrawEntity.builder()
                        .lastDrawMovieId(movieEntity.getId())
                        .build()));
    }
}
