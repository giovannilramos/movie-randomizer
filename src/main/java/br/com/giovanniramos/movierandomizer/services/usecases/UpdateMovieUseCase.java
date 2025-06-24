package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.BadRequestException;
import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.models.MovieModel;
import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movierandomizer.mappers.MovieMapper.MOVIE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateMovieUseCase {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public MovieModel execute(final MovieModel movieModel) {
        final var movieEntity = movieRepository.findById(movieModel.getId()).orElseThrow(() -> {
            log.error("Id not found for update movie. id: {}", movieModel.getId());
            return new NotFoundException("Movie not found");
        });

        validateIfExistsGenres(movieModel);
        return MOVIE_MAPPER.mapToMovieModelFromMovieEntity(movieRepository
                .save(MOVIE_MAPPER.updateMovieEntityFromMovieModel(movieModel, movieEntity)));
    }

    private void validateIfExistsGenres(final MovieModel movieModel) {
        if (!movieModel.getGenres().stream().allMatch(genreRepository::existsByName)) {
            log.error("Invalid genre informed when update movie. genres: {}, movieId: {}",
                    movieModel.getGenres(), movieModel.getId());
            throw new BadRequestException("One or more invalid genres informed");
        }
    }
}
