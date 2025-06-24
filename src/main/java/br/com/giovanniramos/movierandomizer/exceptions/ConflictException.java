package br.com.giovanniramos.movierandomizer.exceptions;

public class ConflictException extends BaseException {

    public ConflictException(final String message) {
        super(message, 409);
    }
}
