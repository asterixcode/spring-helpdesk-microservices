package com.asterixcode.authserviceapi.controller.exceptions;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import models.exceptions.StandardError;
import models.exceptions.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  ResponseEntity<StandardError> handleBadCredentialsException(
      final BadCredentialsException ex, final HttpServletRequest request) {
    return ResponseEntity.status(UNAUTHORIZED)
        .body(
            StandardError.builder()
                .timestamp(now())
                .status(UNAUTHORIZED.value())
                .error(UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ValidationError> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex, final HttpServletRequest request) {
    var errors =
        ValidationError.builder()
            .timestamp(now())
            .status(BAD_REQUEST.value())
            .error("Validation Error")
            .message("Exception in validation fields")
            .path(request.getRequestURI())
            .errors(new ArrayList<>())
            .build();

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errors.addError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.badRequest().body(errors);
  }
}
