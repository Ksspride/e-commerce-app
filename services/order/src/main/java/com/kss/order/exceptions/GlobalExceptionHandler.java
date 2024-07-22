package com.kss.order.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {RecordNotFoundException.class,
            BadRequestException.class, ServerException.class, Exception.class})
    public Object handleUncaughtException(Exception ex, HttpServletRequest request) {
        log(ex);

        ErrorResponse response = new ErrorResponse();

        if (ServerException.class.isAssignableFrom(ex.getClass())) {
            response.setStatus(((ServerException) ex).getHttpStatus());
        } else {
            response.setStatus(getHttpStatusForException(ex));
        }
        BindingResult bindingResult = getBindingResult(ex);
        if (bindingResult != null) {
            response.setMessage("There were some problems with your request");
            logger.error("BindingResult errors are found");
            if (bindingResult.hasFieldErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    response.setMessage(fieldError.getDefaultMessage());
                    logger.error("Object {} --> Field {} --> Error {}", fieldError.getObjectName(),
                            fieldError.getField(), fieldError.getDefaultMessage());
                    // response.getFieldErrors().add(new
                    // FieldBindingError(fieldError.getObjectName(), fieldError.getField(),
                    // fieldError.getDefaultMessage()));
                }
            }

            if (bindingResult.hasGlobalErrors()) {
                for (ObjectError error : bindingResult.getGlobalErrors()) {
                    logger.error("Global Error --> Object {} --> Error {}", error.getObjectName(),
                            error.getDefaultMessage());
                }
            }
        } else {
            response.setMessage(ex.getMessage());
        }
        return new ResponseEntity<Object>(response, response.getStatus());

    }

    public static void log(Exception ex) {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
        logger.error("{} caught in {} - {} (Line: {}) - {}", ex.getClass().getSimpleName(), className, methodName,
                lineNumber, ex.getMessage(), ex);

        Throwable rootCause = getRootCause(ex);
        if (rootCause != null) {
            String rClassName = rootCause.getStackTrace()[0].getClassName();
            String rMethodName = rootCause.getStackTrace()[0].getMethodName();
            int rLineNumber = rootCause.getStackTrace()[0].getLineNumber();
            logger.error("Root Cause of the error traced to {} - {} (Line: {}) - {}", rClassName, rMethodName,
                    rLineNumber, rootCause.getMessage());
        }
    }

    public static Throwable getRootCause(Exception ex) {
        if (ex.getCause() != null) {
            Throwable result = ex.getCause();
            while (result.getCause() != null)
                result = result.getCause();

            return result;
        } else {
            return null;
        }
    }

    public static HttpStatus getHttpStatusForException(Exception ex) {
        HttpStatus errorCode;
        if (ex instanceof NoHandlerFoundException) {
            errorCode = HttpStatus.NOT_FOUND;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            errorCode = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            errorCode = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            errorCode = HttpStatus.NOT_ACCEPTABLE;
        } else if (ex instanceof MissingServletRequestParameterException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ServletRequestBindingException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ConversionNotSupportedException) {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof TypeMismatchException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotReadableException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotWritableException) {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof MethodArgumentNotValidException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof MissingServletRequestPartException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof BindException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof BusinessException) {
            errorCode = HttpStatus.BAD_REQUEST;
        } else {
            logger.warn("Unknown exception type: {}", ex.getClass().getName());
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return errorCode;
    }

    public static BindingResult getBindingResult(Exception ex) {

        if (ex instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) ex).getBindingResult();
        } else if (ex instanceof BindException) {
            return ((BindException) ex).getBindingResult();
        }

        return null;
    }
}
