package br.com.giovanniramos.movie_randomizer.exceptions;

public class ConflictException extends BaseException {

    public ConflictException(final String message) {
        super(message, 409);
    }
}
