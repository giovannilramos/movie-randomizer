package br.com.giovanniramos.movierandomizer.controllers.requests;

import br.com.giovanniramos.movierandomizer.enums.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Set;

public record MovieCreateRequest(
        @NotBlank
        @Schema(description = "Movie name")
        String name,

        @NotNull
        @Schema(description = "Movie cover")
        MultipartFile movieCover,

        @NotNull
        @NotEmpty
        @Schema(description = "Movie genres")
        Set<String> genres,

        @NotNull
        @Schema(description = "Movie type")
        MovieTypeEnum movieType,

        @Schema(description = "Is the first time watching this movie", nullable = true)
        Boolean isFirstTimeWatching,

        @NotBlank
        @Schema(description = "Who is adding this movie")
        String addedBy,

        @Min(0)
        @Max(10)
        @Schema(description = "The note you give to this movie", minimum = "0", maximum = "10", nullable = true)
        Integer note,

        @Schema(description = "Comments you wanna make about this movie", nullable = true)
        String comments,

        @NotNull
        @Schema(description = "Comments you wanna make about this movie")
        LocalTime duration
) {
}
