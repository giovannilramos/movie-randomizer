package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.models.MovieModel;
import br.com.giovanniramos.movierandomizer.repositories.LastMovieDrawRepository;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import br.com.giovanniramos.movierandomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movierandomizer.mappers.MovieMapper.MOVIE_MAPPER;

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
                .orElseThrow(() -> new NotFoundException("No movies watched yet"));
    }

    private MovieModel getMovieCover(final MovieModel movieModel) {
        return movieModel.toBuilder().movieCoverUrl(minioService.getMinioObjectUrl(movieModel.getMovieCoverId())).build();
    }
}
