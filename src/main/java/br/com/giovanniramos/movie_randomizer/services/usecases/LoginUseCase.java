package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.configs.security.service.TokenService;
import br.com.giovanniramos.movie_randomizer.exceptions.UnauthorizedException;
import br.com.giovanniramos.movie_randomizer.models.LoginModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginModel execute(final LoginModel loginModel) {
        try {
            final var authenticatedUser = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));
            final var user = (User) authenticatedUser.getPrincipal();
            return tokenService.generateToken(user.getUsername());
        } catch (final BadCredentialsException badCredentialsException) {
            log.error("Login error. Invalid information informed. username: {}, error: {}",
                    loginModel.getUsername(), badCredentialsException.getMessage(), badCredentialsException);
            throw new UnauthorizedException("Username or password invalid", badCredentialsException);
        }
    }
}
