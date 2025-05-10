package br.com.giovanniramos.movie_randomizer.controllers.responses;

import io.swagger.v3.oas.annotations.media.Schema;

public record GenreResponse(
        @Schema(description = "Genre name", example = "ACTION")
        String name,
        @Schema(description = "Genre description", example = "Ação")
        String description) {
}
