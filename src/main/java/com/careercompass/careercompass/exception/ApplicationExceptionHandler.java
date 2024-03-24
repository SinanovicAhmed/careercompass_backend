package com.careercompass.careercompass.exception;

import com.careercompass.careercompass.model.APIError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIError> handleEntityNotFound(EntityNotFoundException ex) {
        APIError error = new APIError();

        error.setTimestamp(new Date().toString());
        error.setError(ex.getMessage());
        error.setStatus(404);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
