package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.exceptions.BadRequestException;
import br.com.giovanniramos.movie_randomizer.exceptions.ConflictException;
import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.repositories.GenreRepository;
import br.com.giovanniramos.movie_randomizer.repositories.MovieRepository;
import br.com.giovanniramos.movie_randomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.giovanniramos.movie_randomizer.mappers.MovieMapper.MOVIE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddMovieUseCase {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MinioService minioService;

    public MovieModel execute(final MovieModel movieModel) {
        validateIfExistsGenres(movieModel);
        validateIfExistsMovieByName(movieModel.getName());

        final var movieCoverId = UUID.randomUUID().toString();
        minioService.putMinioObject(movieModel.getMovieCover(), movieCoverId);

        return MOVIE_MAPPER.mapToMovieModelFromMovieEntity(
                        movieRepository.save(MOVIE_MAPPER.mapToMovieEntityFromMovieModel(movieModel.toBuilder()
                                .movieCoverId(movieCoverId)
                                .build()))
                )
                .toBuilder().movieCoverUrl(minioService.getMinioObjectUrl(movieCoverId)).build();
    }

    private void validateIfExistsGenres(final MovieModel movieModel) {
        if (!movieModel.getGenres().stream().allMatch(genreRepository::existsByName)) {
            log.error("Invalid genre informed when create movie. genres: {}, movieName: {}",
                    movieModel.getGenres(), movieModel.getName());
            throw new BadRequestException("One or more invalids genres informed");
        }
    }

    private void validateIfExistsMovieByName(final String name) {
        if (movieRepository.existsByNameIgnoreCase(name)) {
            log.error("Movie already exists. movieName: {}", name);
            throw new ConflictException("Movie already exists");
        }
    }
}
