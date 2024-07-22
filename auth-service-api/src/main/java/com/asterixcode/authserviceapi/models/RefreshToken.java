package com.asterixcode.authserviceapi.models;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document
public class RefreshToken {
  @Id private String id;
  private String username;
  private LocalDateTime createdAt;
  private LocalDateTime expiresAt;
}
