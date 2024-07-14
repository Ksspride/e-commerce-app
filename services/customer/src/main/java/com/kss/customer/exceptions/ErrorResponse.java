package com.kss.customer.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus status;
    private String message = "Your request could not be processed at this time. Please contact Support";
    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse() {}

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
