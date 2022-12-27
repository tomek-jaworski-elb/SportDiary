package com.jaworski.sportdiary.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalControllerExceptionHandler.class);
    public static final String MESSAGE = "Exception: {}";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleConflict(Exception e) {
        LOGGER.error("Exception: {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        // Nothing to do
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<String> handleException(RestClientResponseException e) {
        LOGGER.error(MESSAGE, e.getMessage());
        return ResponseEntity.status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        // Nothing to do
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        // Nothing to do
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        LOGGER.error(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        // Nothing to do
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handleIllegalAccessException(IllegalAccessException e) {
        LOGGER.error(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        // Nothing to do
    }
}
