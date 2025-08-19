package br.com.giovanniramos.movierandomizer.services;

import br.com.giovanniramos.movierandomizer.services.usecases.LoginUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.giovanniramos.movierandomizer.mocks.LoginMock.loginModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private LoginUseCase loginUseCase;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldExecuteLogin() {
        final var loginModelMock = loginModelMock();

        when(loginUseCase.execute(any())).thenReturn(loginModelMock);

        final var loginModel = assertDoesNotThrow(() -> userService.login(loginModelMock));

        assertEquals(loginModelMock.getToken(), loginModel.getToken());
    }
}