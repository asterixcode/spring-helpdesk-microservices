package models.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {
  public RefreshTokenExpiredException(final String message) {
    super(message);
  }
}
