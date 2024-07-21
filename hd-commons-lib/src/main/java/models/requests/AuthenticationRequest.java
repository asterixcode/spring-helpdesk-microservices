package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

public record AuthenticationRequest(
    @Schema(description = "User email", example = "lucas@mail.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email cannot be empty")
        @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
        String email,
    @Schema(description = "User password", example = "123456")
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
        String password)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
