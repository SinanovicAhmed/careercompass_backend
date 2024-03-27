package com.careercompass.careercompass.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        DefaultErrorResponse error = new DefaultErrorResponse();

        error.setTimestamp(new Date().toString());
        error.setMessage(ex.getMessage());
        error.setStatus(404);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidArgumentsErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        InvalidArgumentsErrorResponse error = new InvalidArgumentsErrorResponse(
                new Date().toString(),
                "Invalid arguments provided",
                400,
                errors
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
