package br.com.giovanniramos.movierandomizer.exceptions;

public class BadRequestException extends BaseException {
    public BadRequestException(final String message) {
        super(message, 400);
    }
}
