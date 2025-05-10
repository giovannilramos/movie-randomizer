package br.com.giovanniramos.movie_randomizer.controllers;

import br.com.giovanniramos.movie_randomizer.controllers.requests.MovieCreateRequest;
import br.com.giovanniramos.movie_randomizer.controllers.requests.MovieDrawRequest;
import br.com.giovanniramos.movie_randomizer.controllers.requests.MovieListParamsRequest;
import br.com.giovanniramos.movie_randomizer.controllers.requests.MovieUpdateRequest;
import br.com.giovanniramos.movie_randomizer.controllers.responses.MovieResponse;
import br.com.giovanniramos.movie_randomizer.controllers.responses.PageResponse;
import br.com.giovanniramos.movie_randomizer.controllers.responses.UpdateMovieResponse;
import br.com.giovanniramos.movie_randomizer.handlers.responses.ExceptionResponse;
import br.com.giovanniramos.movie_randomizer.handlers.responses.ValidatorsExceptionResponse;
import br.com.giovanniramos.movie_randomizer.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import static br.com.giovanniramos.movie_randomizer.mappers.MovieMapper.MOVIE_MAPPER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movies")
@Tag(name = "Movies", description = "Movies routes")
public class MovieController {
    private final MovieService movieService;

    @Operation(
            summary = "Movie add",
            description = "Add a new movie to list",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Movie added successfully",
                            content = @Content(schema = @Schema(implementation = MovieResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ValidatorsExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieResponse> addMovie(@ModelAttribute @Valid final MovieCreateRequest movieCreateRequest,
                                                  final UriComponentsBuilder uriComponentsBuilder) {
        final var movieModel = movieService.addMovie(MOVIE_MAPPER.mapToMovieModelFromMovieRequest(movieCreateRequest));
        return ResponseEntity.created(uriComponentsBuilder.path("/v1/movies/{id}").buildAndExpand(movieModel.getId()).toUri())
                .body(MOVIE_MAPPER.mapToMovieResponseFromMovieModel(movieModel));
    }

    @Operation(
            summary = "Movie list",
            description = "Show movies registered into the application with pagination",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie values returned successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<PageResponse<MovieResponse>> showMovieList(@ParameterObject final MovieListParamsRequest movieListParamsRequest,
                                                                     @PageableDefault(sort = "name", direction = Sort.Direction.ASC)
                                                                     @ParameterObject final Pageable pageable) {
        return ResponseEntity
                .ok(MOVIE_MAPPER.mapToMoviePageResponseFromPage(movieService.showMovieList(
                        MOVIE_MAPPER.mapToMovieModelFromMovieListParamsRequest(movieListParamsRequest), pageable)));
    }

    @Operation(
            summary = "Movie details",
            description = "Show movie details",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie details returned successfully",
                            content = @Content(schema = @Schema(implementation = MovieResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieDetails(@Parameter(description = "Movie identification")
                                                         @PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(MOVIE_MAPPER.mapToMovieResponseFromMovieModel(movieService.getMovieDetails(id)));
    }

    @Operation(
            summary = "Movie draw",
            description = "Draw one movie between all informed",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie draw successfully",
                            content = @Content(schema = @Schema(implementation = MovieResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ValidatorsExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/draw")
    public ResponseEntity<MovieResponse> movieDraw(@RequestBody @Valid final MovieDrawRequest movieDrawRequest) {
        return ResponseEntity.ok(MOVIE_MAPPER.mapToMovieResponseFromMovieModel(movieService.
                movieDraw(movieDrawRequest.moviesId())));
    }

    @Operation(
            summary = "Movie update",
            description = "Update movie data",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie updated successfully",
                            content = @Content(schema = @Schema(implementation = UpdateMovieResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ValidatorsExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UpdateMovieResponse> updateMovie(@Parameter(description = "Movie identification")
                                                           @PathVariable(value = "id") final String id,
                                                           @RequestBody @Valid final MovieUpdateRequest movieUpdateRequest) {
        return ResponseEntity.ok(MOVIE_MAPPER.mapToUpdateMovieResponseFromMovieModel(movieService
                .updateMovie(MOVIE_MAPPER.mapToMovieModelFromUpdateRequest(id, movieUpdateRequest))));
    }

    @Operation(
            summary = "Movie cover update",
            description = "Update movie cover",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Movie cover updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @PatchMapping("/{id}/cover")
    public ResponseEntity<Void> updateMovieCover(@Parameter(description = "Movie identification")
                                                 @PathVariable(value = "id") final String id,
                                                 @RequestPart final MultipartFile movieCover) {
        movieService.updateMovieCover(id, movieCover);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Last movie watched",
            description = "Returns the last movie watched",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Last movie watched returned successfully",
                            content = @Content(schema = @Schema(implementation = MovieResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/last-watched")
    public ResponseEntity<MovieResponse> lastWatchedMovie() {
        return ResponseEntity.ok(MOVIE_MAPPER.mapToMovieResponseFromMovieModel(movieService.lastWatchedMovie()));
    }

    @Operation(
            summary = "Remove movie",
            description = "Remove one movie by the movie identification informed",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Movie removed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMovie(@Parameter(description = "Movie identification")
                                                     @PathVariable(value = "id") final String id) {
        movieService.removeMovie(id);
        return ResponseEntity.noContent().build();
    }
}
