package br.com.giovanniramos.movierandomizer.controllers.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record MovieDrawRequest(
        @NotNull
        @NotEmpty
        @Schema(description = "Movies identifications that will be draw")
        Set<String> moviesId
) {
}
