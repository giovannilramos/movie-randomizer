package br.com.giovanniramos.movierandomizer.mocks;

import br.com.giovanniramos.movierandomizer.controllers.requests.MovieCreateRequest;
import br.com.giovanniramos.movierandomizer.controllers.requests.MovieListParamsRequest;
import br.com.giovanniramos.movierandomizer.controllers.requests.MovieUpdateRequest;
import br.com.giovanniramos.movierandomizer.entities.MovieEntity;
import br.com.giovanniramos.movierandomizer.models.MovieModel;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static br.com.giovanniramos.movierandomizer.enums.MovieTypeEnum.MOVIE;
import static br.com.giovanniramos.movierandomizer.enums.MovieTypeEnum.SERIES;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreModelMock;

public class MovieMock {
    public static MovieModel movieModelMock() {
        return MovieModel.builder()
                .id(UUID.randomUUID().toString())
                .name("Name")
                .description("Description")
                .movieCoverUrl("https://google.com")
                .movieCoverId(UUID.randomUUID().toString())
                .genres(Set.of(genreModelMock().getName()))
                .movieType(MOVIE)
                .isFirstTimeWatching(true)
                .addedBy("Tester")
                .note(10)
                .comments("Comment")
                .duration(LocalTime.of(1, 30))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MovieModel movieModelWithMovieCoverMock(final MultipartFile movieCover) {
        return movieModelMock()
                .toBuilder()
                .movieCover(movieCover)
                .build();
    }

    public static MovieCreateRequest movieCreateRequestMock() {
        return new MovieCreateRequest(
                "NameEntity",
                "Description",
                new MockMultipartFile("movieCoverEntity", "cover.jpg", MediaType.IMAGE_JPEG_VALUE, "file".getBytes()),
                Set.of(genreModelMock().getName()),
                SERIES,
                false,
                "TesterEntity",
                9,
                "CommentEntity",
                LocalTime.of(2, 30)
        );
    }

    public static MovieEntity movieEntityMock() {
        return MovieEntity.builder()
                .id(UUID.randomUUID().toString())
                .name("Name")
                .description("Description")
                .movieCoverId(UUID.randomUUID().toString())
                .genres(Set.of(genreModelMock().getName()))
                .movieType(MOVIE)
                .isFirstTimeWatching(true)
                .addedBy("Tester")
                .note(10)
                .comments("Comment")
                .duration(LocalTime.of(1, 30))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MovieListParamsRequest movieListParamsRequestMock() {
        return new MovieListParamsRequest(
                "Name",
                Set.of(genreModelMock().getName()),
                MOVIE,
                true,
                "Tester",
                10,
                LocalTime.of(1, 30)
        );
    }

    public static MovieUpdateRequest movieUpdateRequestMock() {
        return new MovieUpdateRequest(
                "Name",
                "Description",
                Set.of(genreModelMock().getName()),
                MOVIE,
                true,
                "Tester",
                10,
                "Comment",
                LocalTime.of(1, 30)
        );
    }
}
