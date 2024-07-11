package models.responses;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import models.enums.ProfileEnum;

public record UserResponse(
    String id, String name, String email, String password, Set<ProfileEnum> profiles)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
