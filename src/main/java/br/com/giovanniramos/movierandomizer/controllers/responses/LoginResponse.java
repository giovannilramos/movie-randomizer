package br.com.giovanniramos.movierandomizer.controllers.responses;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "Response token for authenticate on the calls")
        String token,
        @Schema(description = "Token expires time")
        Long expiresIn) {
}
