package br.com.giovanniramos.movierandomizer.mappers;

import br.com.giovanniramos.movierandomizer.controllers.requests.MovieCreateRequest;
import br.com.giovanniramos.movierandomizer.controllers.requests.MovieListParamsRequest;
import br.com.giovanniramos.movierandomizer.controllers.requests.MovieUpdateRequest;
import br.com.giovanniramos.movierandomizer.controllers.responses.MovieResponse;
import br.com.giovanniramos.movierandomizer.controllers.responses.PageResponse;
import br.com.giovanniramos.movierandomizer.controllers.responses.UpdateMovieResponse;
import br.com.giovanniramos.movierandomizer.entities.MovieEntity;
import br.com.giovanniramos.movierandomizer.models.MovieModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
    MovieMapper MOVIE_MAPPER = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "pageNumber", source = "movieModelPage.pageable.pageNumber")
    @Mapping(target = "pageSize", source = "movieModelPage.pageable.pageSize")
    PageResponse<MovieResponse> mapToMoviePageResponseFromPage(final Page<MovieModel> movieModelPage);

    MovieResponse mapToMovieResponseFromMovieModel(final MovieModel movieModel);

    UpdateMovieResponse mapToUpdateMovieResponseFromMovieModel(final MovieModel movieModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movieCoverId", ignore = true)
    @Mapping(target = "movieCoverUrl", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isFirstTimeWatching", source = "isFirstTimeWatching", defaultValue = "false")
    MovieModel mapToMovieModelFromMovieRequest(final MovieCreateRequest movieCreateRequest);

    @Mapping(target = "movieCover", ignore = true)
    @Mapping(target = "movieCoverUrl", ignore = true)
    MovieModel mapToMovieModelFromMovieEntity(final MovieEntity movieEntity);

    MovieEntity mapToMovieEntityFromMovieModel(final MovieModel movieModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "movieCover", ignore = true)
    @Mapping(target = "movieCoverId", ignore = true)
    @Mapping(target = "movieCoverUrl", ignore = true)
    MovieModel mapToMovieModelFromMovieListParamsRequest(final MovieListParamsRequest movieListParamsRequest);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "movieCover", ignore = true)
    @Mapping(target = "movieCoverId", ignore = true)
    @Mapping(target = "movieCoverUrl", ignore = true)
    MovieModel mapToMovieModelFromUpdateRequest(final String id, final MovieUpdateRequest movieUpdateRequest);

    default MovieEntity updateMovieEntityFromMovieModel(final MovieModel movieModel, final MovieEntity movieEntity) {
        return movieEntity.toBuilder()
                .name(movieModel.getName())
                .genres(movieModel.getGenres())
                .movieType(movieModel.getMovieType())
                .isFirstTimeWatching(movieModel.getIsFirstTimeWatching())
                .addedBy(movieModel.getAddedBy())
                .note(movieModel.getNote())
                .comments(movieModel.getComments())
                .duration(movieModel.getDuration())
                .build();
    }
}
