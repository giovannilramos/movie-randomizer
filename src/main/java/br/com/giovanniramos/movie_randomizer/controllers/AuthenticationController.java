package br.com.giovanniramos.movie_randomizer.controllers;

import br.com.giovanniramos.movie_randomizer.controllers.requests.LoginRequest;
import br.com.giovanniramos.movie_randomizer.controllers.responses.LoginResponse;
import br.com.giovanniramos.movie_randomizer.handlers.responses.ExceptionResponse;
import br.com.giovanniramos.movie_randomizer.handlers.responses.ValidatorsExceptionResponse;
import br.com.giovanniramos.movie_randomizer.models.LoginModel;
import br.com.giovanniramos.movie_randomizer.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/login")
@Tag(name = "Login", description = "Login routes")
public class AuthenticationController {
    private final UserService userService;

    @Operation(
            summary = "Login into the application",
            description = "Generate access token to access the routes of the application",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successfully",
                            content = @Content(schema = @Schema(implementation = LoginResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ValidatorsExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        final var loginModel = userService.login(LoginModel.builder()
                .username(loginRequest.username())
                .password(loginRequest.password())
                .build());
        return ResponseEntity.ok(new LoginResponse(loginModel.getToken(), loginModel.getExpiresIn()));
    }
}
