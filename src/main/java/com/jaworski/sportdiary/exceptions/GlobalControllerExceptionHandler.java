package com.jaworski.sportdiary.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ResponseStatus(code = HttpStatus.NOT_FOUND)  // 409
    @ExceptionHandler(Exception.class)
    public void handleConflict() {
        System.out.println("DataIntegrityViolationException");
        // Nothing to do
    }
}
