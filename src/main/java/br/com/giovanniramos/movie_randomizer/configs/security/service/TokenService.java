package br.com.giovanniramos.movie_randomizer.configs.security.service;

import br.com.giovanniramos.movie_randomizer.exceptions.UnauthorizedException;
import br.com.giovanniramos.movie_randomizer.models.LoginModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class TokenService {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String JWT_ISSUER = "API Movie Randomizer";

    private final Integer expTime;
    private final String secret;

    public TokenService(@Value("${jwt.expiration}") final Integer expTime, @Value("${jwt.secret}") final String secret) {
        this.expTime = expTime;
        this.secret = secret;
    }

    public LoginModel generateToken(final String username) {
        final var expiresIn = Instant.ofEpochMilli(
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli() + expTime
        );

        final var token = JWT.create()
                .withIssuer(JWT_ISSUER)
                .withSubject(username)
                .withExpiresAt(expiresIn)
                .sign(Algorithm.HMAC256(secret));

        return LoginModel.builder()
                .token(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();
    }

    public String getSubject(final HttpServletRequest request) {
        final var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isNull(token) || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        try {
            final var username = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(JWT_ISSUER)
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (isNull(username) || username.isEmpty()) {
                return null;
            }

            return username;
        } catch (final JWTCreationException jwtCreationException) {
            log.error("Invalid JWT token informed. Error: {}", jwtCreationException.getMessage(), jwtCreationException);
            throw new UnauthorizedException("Invalid JWT token", jwtCreationException);
        }
    }
}
