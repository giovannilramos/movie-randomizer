package br.com.giovanniramos.movierandomizer.controllers.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record GenreCreateRequest(
        @NotBlank
        @Schema(description = "Genre name", example = "ACTION")
        String name,

        @NotBlank
        @Schema(description = "Genre description", example = "Ação")
        String description) {
}
