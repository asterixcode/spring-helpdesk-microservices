package com.asterixcode.userserviceapi.controller.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import org.springframework.http.ResponseEntity;
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
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build());
  }
}
