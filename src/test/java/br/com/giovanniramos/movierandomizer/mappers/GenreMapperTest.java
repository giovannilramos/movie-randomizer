package br.com.giovanniramos.movierandomizer.mappers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.giovanniramos.movierandomizer.mappers.GenreMapper.GENRE_MAPPER;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreCreateRequestMock;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreEntityMock;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreModelMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GenreMapperTest {
    @Test
    void shouldMapToGenreModelFromGenreCreateRequest() {
        final var genreCreateRequest = genreCreateRequestMock();
        final var genreModel = GENRE_MAPPER.mapToGenreModelFromGenreCreateRequest(genreCreateRequest);

        assertEquals("ACTION", genreModel.getName());
        assertEquals("Ação", genreModel.getDescription());
    }

    @Test
    void shouldMapToGenreModelFromGenreEntity() {
        final var genreEntity = genreEntityMock();

        final var genreModel = GENRE_MAPPER.mapToGenreModelFromGenreEntity(genreEntity);

        assertEquals(genreEntity.getId(), genreModel.getId());
        assertEquals(genreEntity.getName(), genreModel.getName());
        assertEquals(genreEntity.getDescription(), genreModel.getDescription());
    }

    @Test
    void shouldMapToGenreEntityFromGenreModel() {
        final var genreModel = genreModelMock();

        final var genreEntity = GENRE_MAPPER.mapToGenreEntityFromGenreModel(genreModel);

        assertEquals(genreModel.getId(), genreEntity.getId());
        assertEquals(genreModel.getName(), genreEntity.getName());
        assertEquals(genreModel.getDescription(), genreEntity.getDescription());
    }

    @Test
    void shouldMapToGenreModelListFromGenreEntityList() {
        final var genreEntity = genreEntityMock();

        final var genreModel = GENRE_MAPPER.mapToGenreModelListFromGenreEntityList(List.of(genreEntity))
                .getFirst();

        assertEquals(genreEntity.getId(), genreModel.getId());
        assertEquals(genreEntity.getName(), genreModel.getName());
        assertEquals(genreEntity.getDescription(), genreModel.getDescription());
    }

    @Test
    void shouldMapToGenreResponseFromGenreModel() {
        final var genreModel = genreModelMock();

        final var genreResponse = GENRE_MAPPER.mapToGenreResponseFromGenreModel(genreModel);

        assertEquals(genreResponse.name(), genreModel.getName());
        assertEquals(genreResponse.description(), genreModel.getDescription());
    }

    @Test
    void shouldMapToGenreResponseListFromGenreModelList() {
        final var genreModel = genreModelMock();

        final var genreResponse = GENRE_MAPPER.mapToGenreResponseListFromGenreModelList(List.of(genreModel))
                .getFirst();

        assertEquals(genreResponse.name(), genreModel.getName());
        assertEquals(genreResponse.description(), genreModel.getDescription());
    }

    @Test
    void shouldReturnNull() {
        assertNull(GENRE_MAPPER.mapToGenreModelFromGenreCreateRequest(null));
        assertNull(GENRE_MAPPER.mapToGenreModelFromGenreEntity(null));
        assertNull(GENRE_MAPPER.mapToGenreEntityFromGenreModel(null));
        assertNull(GENRE_MAPPER.mapToGenreModelListFromGenreEntityList(null));
        assertNull(GENRE_MAPPER.mapToGenreResponseFromGenreModel(null));
        assertNull(GENRE_MAPPER.mapToGenreResponseListFromGenreModelList(null));
    }
}