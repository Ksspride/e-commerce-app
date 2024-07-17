package com.kss.product.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ProductPurchaseException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -3764254971050744098L;

    public ProductPurchaseException(String message) {
        super(message);
    }

    public ProductPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
