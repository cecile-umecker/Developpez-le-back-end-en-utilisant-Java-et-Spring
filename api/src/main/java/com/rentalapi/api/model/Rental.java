package com.rentalapi.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a rental property in the system.
 * This class maps to the RENTALS table in the database.
 *
 * @Entity annotation indicates that this class is a JPA entity
 * @Table annotation specifies the table name in the database
 */

@Entity
@Table(name = "RENTALS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private Float surface;
  private Float price;
  private String picture;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner_id;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
