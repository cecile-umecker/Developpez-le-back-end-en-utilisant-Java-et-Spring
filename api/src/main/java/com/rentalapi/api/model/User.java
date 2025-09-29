package com.rentalapi.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a User entity in the rental system.
 * This class is mapped to the "USERS" table in the database.
 *
 * @Entity annotation indicates that this class is a JPA entity
 * @Table specifies the table name in the database
 * @Data Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode methods
 * @NoArgsConstructor Lombok annotation to generate a no-args constructor
 * @AllArgsConstructor Lombok annotation to generate a constructor with all arguments
 * @Builder Lombok annotation to implement the Builder pattern
 */

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String name;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
