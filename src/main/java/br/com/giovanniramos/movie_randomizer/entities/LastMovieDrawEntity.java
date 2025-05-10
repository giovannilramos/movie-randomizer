package br.com.giovanniramos.movie_randomizer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "lastMovieDraw")
public class LastMovieDrawEntity {
    @Id
    private String id;
    private String lastDrawMovieId;
}
