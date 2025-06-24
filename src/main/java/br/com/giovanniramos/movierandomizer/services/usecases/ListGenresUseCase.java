package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.models.GenreModel;
import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.giovanniramos.movierandomizer.mappers.GenreMapper.GENRE_MAPPER;

@Service
@RequiredArgsConstructor
public class ListGenresUseCase {
    private final GenreRepository genreRepository;

    public List<GenreModel> execute() {
        return GENRE_MAPPER.mapToGenreModelListFromGenreEntityList(genreRepository.findAll(Sort.by("name")));
    }
}
