package br.com.giovanniramos.movierandomizer.exceptions;

public class InternalServerErrorException extends BaseException {

    public InternalServerErrorException(final String message, final Throwable cause) {
        super(message, 500, cause);
    }
}
