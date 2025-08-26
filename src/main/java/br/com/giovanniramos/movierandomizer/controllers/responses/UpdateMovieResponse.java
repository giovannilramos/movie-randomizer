package br.com.giovanniramos.movierandomizer.controllers.responses;

import br.com.giovanniramos.movierandomizer.enums.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public record UpdateMovieResponse(
        @Schema(description = "Movie identification")
        String id,

        @Schema(description = "Movie name")
        String name,

        @Schema(description = "Movie description")
        String description,

        @Schema(description = "Movie genres")
        Set<String> genres,

        @Schema(description = "Movie type")
        MovieTypeEnum movieType,

        @Schema(description = "Is the first time watching this movie")
        Boolean isFirstTimeWatching,

        @Schema(description = "Who is added this movie")
        String addedBy,

        @Schema(description = "The note you gave to this movie", minimum = "0", maximum = "10")
        Integer note,

        @Schema(description = "Comments you made about this movie")
        String comments,

        @Schema(description = "Comments you made about this movie")
        LocalTime duration,

        @Schema(description = "Movie creation datetime")
        LocalDateTime createdAt
) {
}
