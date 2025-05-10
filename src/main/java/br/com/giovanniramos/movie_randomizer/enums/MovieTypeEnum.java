package br.com.giovanniramos.movie_randomizer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieTypeEnum {
    MOVIE("Filme"),
    SERIES("Série"),
    ANIME("Anime");

    private final String description;
}
