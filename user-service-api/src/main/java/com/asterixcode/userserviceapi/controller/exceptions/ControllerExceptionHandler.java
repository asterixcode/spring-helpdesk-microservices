package com.asterixcode.userserviceapi.controller.exceptions;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import models.exceptions.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<StandardError> handleResourceNotFoundException(
      final ResourceNotFoundException e, final HttpServletRequest request) {
    return ResponseEntity.status(NOT_FOUND)
        .body(
            StandardError.builder()
                .timestamp(now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ValidationError> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException e, final HttpServletRequest request) {
    var errors =
        ValidationError.builder()
            .timestamp(now())
            .status(BAD_REQUEST.value())
            .error("Validation Error")
            .message("Exception in validation fields")
            .path(request.getRequestURI())
            .errors(new ArrayList<>())
            .build();

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      errors.addError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.badRequest().body(errors);
  }
}
