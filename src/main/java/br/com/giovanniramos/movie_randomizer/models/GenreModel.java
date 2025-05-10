package br.com.giovanniramos.movie_randomizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreModel {
    private String id;
    private String name;
    private String description;
}
