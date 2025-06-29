package br.com.giovanniramos.movierandomizer.mocks;

import br.com.giovanniramos.movierandomizer.controllers.requests.GenreCreateRequest;
import br.com.giovanniramos.movierandomizer.models.GenreModel;

import java.util.UUID;

public class GenreMock {

    public static GenreCreateRequest genreCreateRequestMock() {
        return new GenreCreateRequest("ACTION", "Ação");
    }

    public static GenreModel genreModelMock() {
        return GenreModel.builder()
                .id(UUID.randomUUID().toString())
                .name("ACTION")
                .description("Ação")
                .build();
    }
}
