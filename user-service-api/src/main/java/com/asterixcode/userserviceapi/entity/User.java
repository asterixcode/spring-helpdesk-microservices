package com.asterixcode.userserviceapi.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import models.enums.ProfileEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

  @Id
  private String id;
  private String name;
  private String email;
  private String password;
  private Set<ProfileEnum> profiles;
}
