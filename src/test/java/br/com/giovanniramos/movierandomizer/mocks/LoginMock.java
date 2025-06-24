package br.com.giovanniramos.movierandomizer.mocks;

import br.com.giovanniramos.movierandomizer.models.LoginModel;
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
