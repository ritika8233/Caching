package com.example.reactdemo.exception;

import java.util.HashMap;
import java.util.Map;

public class DataValidationException extends RuntimeException{
    private static final long serialVersionUID = -526313233133876822L;

    public DataValidationException() {
    }

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationException(Throwable cause) {
        super(cause);
    }

    public DataValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
