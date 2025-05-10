package br.com.giovanniramos.movie_randomizer.controllers.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record MovieDrawRequest(
        @NotNull
        @Schema(description = "Movies identifications that will be draw")
        Set<String> moviesId
) {
}
