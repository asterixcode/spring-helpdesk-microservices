package models.exceptions;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationError extends StandardError {

  private final List<FieldError> errors;

  private record FieldError(String fieldName, String message) {}

  public void addError(final String fieldName, final String message) {
    this.errors.add(new FieldError(fieldName, message));
  }
}
