package br.com.giovanniramos.movierandomizer.exceptions;

public class NotFoundException extends BaseException {

    public NotFoundException(final String message) {
        super(message, 404);
    }
}
