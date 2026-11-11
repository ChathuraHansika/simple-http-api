package com.example.simplehttpapi.exception;

import com.example.simplehttpapi.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.tomcat.util.http.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions and returns an error response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final ErrorResponse INVALID_INPUT = new ErrorResponse("Invalid Input");

    /**
     * Handles invalid input exceptions and returns an error response.
     */
    @ExceptionHandler(com.example.simplehttpapi.exception.InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInput(com.example.simplehttpapi.exception.InvalidInputException exception) {
        log.warn("Rejected request: {}", exception.getMessage());
        return INVALID_INPUT;
    }

    /**
     * Handles invalid parameter exceptions and returns an error response.
     */
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUndecodableQuery() {
        log.warn("Rejected request: query string could not be decoded");
        return INVALID_INPUT;
    }

    /**
     * Handles runtime parameter exceptions and returns an error response.
     */
    @ExceptionHandler(RuntimeException.class)
    public void handleUnexpectedError(RuntimeException exception, HttpServletResponse response)
            throws IOException {
        log.error("Unexpected error while processing request", exception);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
