package br.com.giovanniramos.movie_randomizer.mappers;

import br.com.giovanniramos.movie_randomizer.controllers.requests.GenreCreateRequest;
import br.com.giovanniramos.movie_randomizer.controllers.responses.GenreResponse;
import br.com.giovanniramos.movie_randomizer.entities.GenreEntity;
import br.com.giovanniramos.movie_randomizer.models.GenreModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreMapper GENRE_MAPPER = Mappers.getMapper(GenreMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(genreCreateRequest.name().toUpperCase(java.util.Locale.ROOT))")
    GenreModel mapToGenreModelFromGenreCreateRequest(final GenreCreateRequest genreCreateRequest);

    GenreModel mapToGenreModelFromGenreEntity(final GenreEntity genreEntity);

    GenreEntity mapToGenreEntityFromGenreModel(final GenreModel genreModel);

    List<GenreModel> mapToGenreModelListFromGenreEntityList(final List<GenreEntity> genreEntityList);

    GenreResponse mapToGenreResponseFromGenreModel(final GenreModel genreModel);

    List<GenreResponse> mapToGenreResponseListFromGenreModelList(final List<GenreModel> genreModelList);
}
