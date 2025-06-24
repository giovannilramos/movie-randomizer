package br.com.giovanniramos.movierandomizer.mocks;

import br.com.giovanniramos.movierandomizer.models.MovieModel;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static br.com.giovanniramos.movierandomizer.enums.MovieTypeEnum.MOVIE;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreModelMock;

public class MovieMock {
    public static MovieModel movieModelMock() {
        return MovieModel.builder()
                .id(UUID.randomUUID().toString())
                .name("Name")
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
}
