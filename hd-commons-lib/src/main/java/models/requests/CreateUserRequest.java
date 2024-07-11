package models.requests;

import java.util.Set;
import lombok.With;
import models.enums.ProfileEnum;

@With
public record CreateUserRequest(
    String name, String email, String password, Set<ProfileEnum> profiles) {}
