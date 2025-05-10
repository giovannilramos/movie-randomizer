package br.com.giovanniramos.movie_randomizer.controllers.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Schema(description = "Username for login")
        String username,

        @NotBlank
        @Schema(description = "User password for login")
        String password
) {
}
