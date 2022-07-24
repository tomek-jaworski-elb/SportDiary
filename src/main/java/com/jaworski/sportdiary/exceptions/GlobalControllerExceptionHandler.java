package com.jaworski.sportdiary.exceptions;

import com.jaworski.sportdiary.controller.AnonymousController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(code = HttpStatus.NOT_FOUND)  // 409
    @ExceptionHandler(Exception.class)
    public void handleConflict() {
        LOGGER.error("Exception: " + Exception.class);
        // Nothing to do
    }
}
