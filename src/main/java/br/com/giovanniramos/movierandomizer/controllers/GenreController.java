package br.com.giovanniramos.movierandomizer.controllers;

import br.com.giovanniramos.movierandomizer.controllers.requests.GenreCreateRequest;
import br.com.giovanniramos.movierandomizer.controllers.responses.GenreResponse;
import br.com.giovanniramos.movierandomizer.exceptions.BaseException;
import br.com.giovanniramos.movierandomizer.handlers.responses.ExceptionResponse;
import br.com.giovanniramos.movierandomizer.handlers.responses.ValidatorsExceptionResponse;
import br.com.giovanniramos.movierandomizer.services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.giovanniramos.movierandomizer.mappers.GenreMapper.GENRE_MAPPER;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/genres")
@Tag(name = "Genres", description = "Genres routes")
public class GenreController {
    private final GenreService genreService;

    @Operation(
            summary = "Genre list",
            description = "Show all genres registered into the application",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Genre values returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GenreResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BaseException.class))
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<GenreResponse>> listGenres() {
        return ResponseEntity.ok(GENRE_MAPPER.mapToGenreResponseListFromGenreModelList(genreService.listGenres()));
    }

    @Operation(
            summary = "Create genre",
            description = "Create a new movie genre to flag the movies",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Genre created successfully",
                            content = @Content(schema = @Schema(implementation = GenreResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ValidatorsExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BaseException.class))
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<GenreResponse> addGenre(@RequestBody @Valid final GenreCreateRequest genreCreateRequest) {
        return ResponseEntity.status(CREATED)
                .body(GENRE_MAPPER.mapToGenreResponseFromGenreModel(genreService
                        .addGenre(GENRE_MAPPER.mapToGenreModelFromGenreCreateRequest(genreCreateRequest))));
    }
}
