package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.models.GenreModel;
import br.com.giovanniramos.movie_randomizer.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.giovanniramos.movie_randomizer.mappers.GenreMapper.GENRE_MAPPER;

@Service
@RequiredArgsConstructor
public class ListGenresUseCase {
    private final GenreRepository genreRepository;

    public List<GenreModel> execute() {
        return GENRE_MAPPER.mapToGenreModelListFromGenreEntityList(genreRepository.findAll(Sort.by("name")));
    }
}
