package br.com.giovanniramos.movie_randomizer.services.mocks;

import br.com.giovanniramos.movie_randomizer.models.LoginModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginMock {
    public static LoginModel loginModelMock() {
        return LoginModel.builder()
                .username("username")
                .password(new BCryptPasswordEncoder().encode("password"))
                .expiresIn(10000L)
                .token("token")
                .build();
    }
}
