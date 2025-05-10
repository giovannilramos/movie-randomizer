package br.com.giovanniramos.movie_randomizer.exceptions;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final int code;

    protected BaseException(final String message, final int code) {
        super(message);
        this.code = code;
    }

    protected BaseException(final String message, final int code, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
