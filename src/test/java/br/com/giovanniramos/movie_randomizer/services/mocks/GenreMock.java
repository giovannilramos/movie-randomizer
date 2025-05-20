package br.com.giovanniramos.movie_randomizer.services.mocks;

import br.com.giovanniramos.movie_randomizer.models.GenreModel;

import java.util.UUID;

public class GenreMock {

    public static GenreModel genreModelMock() {
        return GenreModel.builder()
                .id(UUID.randomUUID().toString())
                .name("ACTION")
                .description("Ação")
                .build();
    }
}
