package br.com.giovanniramos.movie_randomizer.services;

import br.com.giovanniramos.movie_randomizer.models.LoginModel;
import br.com.giovanniramos.movie_randomizer.services.usecases.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final LoginUseCase loginUseCase;

    public LoginModel login(final LoginModel loginModel) {
        return loginUseCase.execute(loginModel);
    }
}
