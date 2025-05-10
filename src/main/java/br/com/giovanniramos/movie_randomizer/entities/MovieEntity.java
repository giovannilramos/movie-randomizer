package br.com.giovanniramos.movie_randomizer.entities;

import br.com.giovanniramos.movie_randomizer.enums.MovieTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class MovieEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String movieCoverId;
    @Indexed
    private Set<String> genres;
    @Indexed
    private MovieTypeEnum movieType;
    private Boolean isFirstTimeWatching;
    private String addedBy;
    @Indexed
    private Integer note;
    private String comments;
    private LocalTime duration;
    private LocalDateTime createdAt;
}
