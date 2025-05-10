package br.com.giovanniramos.movie_randomizer.exceptions;

public class NotFoundException extends BaseException {

    public NotFoundException(final String message) {
        super(message, 404);
    }
}
