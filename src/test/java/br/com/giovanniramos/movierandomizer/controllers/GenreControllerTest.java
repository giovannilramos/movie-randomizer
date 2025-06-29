package br.com.giovanniramos.movierandomizer.controllers;

import br.com.giovanniramos.movierandomizer.controllers.requests.GenreCreateRequest;
import br.com.giovanniramos.movierandomizer.exceptions.ConflictException;
import br.com.giovanniramos.movierandomizer.services.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreCreateRequestMock;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreModelMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GenreControllerTest {
    private static final String BASE_URL = "/v1/genres";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenreService genreService;

    private static Stream<Arguments> genreRequestParams() {
        return Stream.of(
                Arguments.of("", "description"),
                Arguments.of(" ", "description"),
                Arguments.of(null, "description"),
                Arguments.of("name", ""),
                Arguments.of("name", " "),
                Arguments.of("name", null)
        );
    }

    @Test
    @SneakyThrows
    void shouldListGenres() {
        when(genreService.listGenres()).thenReturn(List.of(genreModelMock()));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].description").exists());
    }

    @Test
    @SneakyThrows
    void shouldTryToListGenresAndReturn500WhenThrowsUnexpectedException() {
        when(genreService.listGenres()).thenThrow(new RuntimeException());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @SneakyThrows
    void shouldAddGenre() {
        when(genreService.addGenre(any())).thenReturn(genreModelMock());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(genreCreateRequestMock())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists());
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("genreRequestParams")
    void shouldTryToAddGenreAndReturn400WhenBodyIsInvalid(final String name, final String description) {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new GenreCreateRequest(name, description))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void shouldTryToAddGenreAndReturn409WhenGenreAlreadyExists() {
        when(genreService.addGenre(any())).thenThrow(new ConflictException("Genre already exists"));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(genreCreateRequestMock())))
                .andExpect(status().isConflict());
    }

    @Test
    @SneakyThrows
    void shouldTryToAddGenreAndReturn500WhenThrowsUnexpectedException() {
        when(genreService.addGenre(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(genreCreateRequestMock())))
                .andExpect(status().isInternalServerError());
    }
}