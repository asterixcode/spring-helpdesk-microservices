package com.asterixcode.authserviceapi.models;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import models.enums.ProfileEnum;

@Getter
@AllArgsConstructor
public class User {
  private String id;
  private String name;
  private String email;
  private String password;
  private Set<ProfileEnum> profiles;
}
