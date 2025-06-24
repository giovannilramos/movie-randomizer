package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.ConflictException;
import br.com.giovanniramos.movierandomizer.models.GenreModel;
import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.giovanniramos.movierandomizer.mappers.GenreMapper.GENRE_MAPPER;

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
