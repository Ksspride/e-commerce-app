package com.kss.order.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ServerException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 8228624221822217990L;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
