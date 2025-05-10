package br.com.giovanniramos.movie_randomizer.exceptions;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(final String message) {
        super(message, 401);
    }

    public UnauthorizedException(final String message, final Throwable cause) {
        super(message, 401, cause);
    }
}
