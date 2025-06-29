package br.com.giovanniramos.movierandomizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {
    private String username;
    private String password;
    private String token;
    private Long expiresIn;
}
