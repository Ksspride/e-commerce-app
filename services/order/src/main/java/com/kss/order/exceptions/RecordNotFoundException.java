package com.kss.order.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -9188548803395217992L;

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
