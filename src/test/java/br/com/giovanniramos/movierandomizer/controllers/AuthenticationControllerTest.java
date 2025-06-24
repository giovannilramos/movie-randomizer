package br.com.giovanniramos.movierandomizer.controllers;

import br.com.giovanniramos.movierandomizer.controllers.requests.LoginRequest;
import br.com.giovanniramos.movierandomizer.exceptions.UnauthorizedException;
import br.com.giovanniramos.movierandomizer.models.LoginModel;
import br.com.giovanniramos.movierandomizer.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {
    private static final String BASE_URL = "/v1/login";
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @SneakyThrows
    void shouldLogIn() {
        final var username = "giovanni.ramos";
        final var password = "12345";

        when(userService.login(any())).thenReturn(new LoginModel(username, password, "Token", 12456L));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.expiresIn").exists());
    }

    @SneakyThrows
    @ParameterizedTest
    @CsvSource(value = { "' '", "''", "null" }, nullValues = { "null" })
    void shouldTryToLogInAndReturn400WhenUsernameIsInvalid(final String username) {
        final var password = "12345";

        when(userService.login(any())).thenReturn(new LoginModel(username, password, "Token", 12456L));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @ParameterizedTest
    @CsvSource(value = { "' '", "''", "null" }, nullValues = { "null" })
    void shouldTryToLogInAndReturn400WhenPasswordIsInvalid(final String password) {
        final var username = "giovanni.ramos";

        when(userService.login(any())).thenReturn(new LoginModel(username, password, "Token", 12456L));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void shouldTryToLogInAndReturn401WhenUnauthorized() {
        when(userService.login(any())).thenThrow(new UnauthorizedException("Username or password invalid"));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new LoginRequest("giovanni.ramos", "12345"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void shouldTryToLogInAndReturn500WhenThrowUnexpectedException() {
        when(userService.login(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(new LoginRequest("giovanni.ramos", "12345"))))
                .andExpect(status().isInternalServerError());
    }
}