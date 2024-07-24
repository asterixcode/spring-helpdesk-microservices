package com.asterixcode.orderserviceapi.entity;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_orders")
public class Order implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 45)
  private String requesterId;

  @Column(nullable = false, length = 45)
  private String customerId;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false, length = 3000)
  private String description;

  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime closedAt;
}
