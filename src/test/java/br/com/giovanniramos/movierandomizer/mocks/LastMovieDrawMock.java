package br.com.giovanniramos.movierandomizer.mocks;

import br.com.giovanniramos.movierandomizer.entities.LastMovieDrawEntity;

import java.util.UUID;

public class LastMovieDrawMock {
    public static LastMovieDrawEntity lastMovieDrawEntityMock() {
        return LastMovieDrawEntity.builder()
                .id(UUID.randomUUID().toString())
                .lastDrawMovieId(UUID.randomUUID().toString())
                .build();
    }
}
