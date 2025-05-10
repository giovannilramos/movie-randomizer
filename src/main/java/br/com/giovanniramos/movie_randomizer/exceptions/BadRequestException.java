package br.com.giovanniramos.movie_randomizer.exceptions;

public class BadRequestException extends BaseException {
    public BadRequestException(final String message) {
        super(message, 400);
    }
}
