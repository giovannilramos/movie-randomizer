package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.repositories.LastMovieDrawRepository;
import br.com.giovanniramos.movie_randomizer.repositories.MovieRepository;
import br.com.giovanniramos.movie_randomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movie_randomizer.mappers.MovieMapper.MOVIE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastWatchedMovieUseCase {
    private final LastMovieDrawRepository lastMovieDrawRepository;
    private final MovieRepository movieRepository;
    private final MinioService minioService;

    public MovieModel execute() {
        return lastMovieDrawRepository.findFirstBy()
                .map(lastMovieDrawEntity -> MOVIE_MAPPER.mapToMovieModelFromMovieEntity(
                        movieRepository.findById(lastMovieDrawEntity.getLastDrawMovieId()).orElse(null))
                ).map(this::getMovieCover)
                .orElse(null);
    }

    private MovieModel getMovieCover(final MovieModel movieModel) {
        return movieModel.toBuilder().movieCoverUrl(minioService.getMinioObjectUrl(movieModel.getMovieCoverId())).build();
    }
}
