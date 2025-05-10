package br.com.giovanniramos.movie_randomizer.services;

import br.com.giovanniramos.movie_randomizer.models.GenreModel;
import br.com.giovanniramos.movie_randomizer.services.usecases.AddGenreUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.ListGenresUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final AddGenreUseCase addGenreUseCase;
    private final ListGenresUseCase listGenresUseCase;

    public GenreModel addGenre(final GenreModel genreModel) {
        return addGenreUseCase.execute(genreModel);
    }

    public List<GenreModel> listGenres() {
        return listGenresUseCase.execute();
    }
}
