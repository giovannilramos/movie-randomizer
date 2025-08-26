package br.com.giovanniramos.movierandomizer.models;

import br.com.giovanniramos.movierandomizer.enums.MovieTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {
    private String id;
    private String name;
    private String description;
    private MultipartFile movieCover;
    private String movieCoverUrl;
    private String movieCoverId;
    private Set<String> genres;
    private MovieTypeEnum movieType;
    private Boolean isFirstTimeWatching;
    private String addedBy;
    private Integer note;
    private String comments;
    private LocalTime duration;
    private LocalDateTime createdAt;
}
