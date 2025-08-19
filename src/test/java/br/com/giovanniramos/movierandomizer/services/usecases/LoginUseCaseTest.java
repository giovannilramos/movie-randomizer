package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.configs.security.service.TokenService;
import br.com.giovanniramos.movierandomizer.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

import static br.com.giovanniramos.movierandomizer.mocks.LoginMock.loginModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {
    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    void shouldLoginSuccessfully() {
        final var loginModel = loginModelMock();

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new User(loginModel.getUsername(), loginModel.getPassword(), new ArrayList<>()));
        when(tokenService.generateToken(any())).thenReturn(loginModel);

        final var response = assertDoesNotThrow(() -> loginUseCase.execute(loginModel));

        assertEquals(loginModel.getToken(), response.getToken());
    }

    @Test
    void shouldTryToLoginAndReturnUnauthorizedException() {
        final var loginModel = loginModelMock();

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        final var unauthorizedException = assertThrows(UnauthorizedException.class, () -> loginUseCase.execute(loginModel));

        assertEquals(401, unauthorizedException.getCode());
        assertEquals("Username or password invalid", unauthorizedException.getMessage());
    }
}