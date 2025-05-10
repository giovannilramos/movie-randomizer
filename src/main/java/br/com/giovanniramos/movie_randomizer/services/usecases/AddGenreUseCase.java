package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.exceptions.ConflictException;
import br.com.giovanniramos.movie_randomizer.models.GenreModel;
import br.com.giovanniramos.movie_randomizer.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movie_randomizer.mappers.GenreMapper.GENRE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddGenreUseCase {
    private final GenreRepository genreRepository;

    public GenreModel execute(final GenreModel genreModel) {
        validateIfGenreExistsByName(genreModel.getName());
        return GENRE_MAPPER.mapToGenreModelFromGenreEntity(genreRepository
                .save(GENRE_MAPPER.mapToGenreEntityFromGenreModel(genreModel)));
    }

    private void validateIfGenreExistsByName(final String name) {
        if (genreRepository.existsByName(name)) {
            log.error("Genre name already exists. name: {}", name);
            throw new ConflictException("Genre already exists");
        }
    }
}
