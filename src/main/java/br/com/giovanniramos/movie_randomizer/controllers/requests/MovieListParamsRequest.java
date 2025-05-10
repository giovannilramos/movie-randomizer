package br.com.giovanniramos.movie_randomizer.controllers.requests;

import br.com.giovanniramos.movie_randomizer.enums.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.Set;

public record MovieListParamsRequest(
        @Schema(description = "Movie name", nullable = true)
        String name,

        @Schema(description = "Movie genres", nullable = true)
        Set<String> genres,

        @Schema(description = "Movie type", nullable = true)
        MovieTypeEnum movieType,

        @Schema(description = "Is the first time watching this movie", nullable = true)
        Boolean isFirstTimeWatching,

        @Schema(description = "Who is adding this movie", nullable = true)
        String addedBy,

        @Schema(description = "The note you give to this movie", minimum = "0", maximum = "10", nullable = true)
        Integer note,

        @Schema(description = "Comments you wanna make about this movie", nullable = true)
        LocalTime duration
) {
}
