package com.rentalapi.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a message in the rental system.
 * This class maps to the MESSAGES table in the database.
 * 
 * @Entity Indicates that this class is a JPA entity
 * @Table Specifies the table name in the database
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MESSAGES")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String message;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user_id;

  @ManyToOne
  @JoinColumn(name = "rental_id", nullable = false)
  private Rental rental_id;

  private LocalDateTime createdAt;
}
