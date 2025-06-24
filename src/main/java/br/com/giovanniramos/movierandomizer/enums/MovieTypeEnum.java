package br.com.giovanniramos.movierandomizer.enums;

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
