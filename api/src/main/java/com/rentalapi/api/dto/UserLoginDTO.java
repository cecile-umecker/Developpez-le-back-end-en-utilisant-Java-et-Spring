package com.rentalapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user login information.
 * This class is used to transfer login credentials between client and server.
 * 
 * @Data Lombok annotation that generates getters, setters, toString, equals and hashCode methods
 */

@Data
public class UserLoginDTO {
  @NotBlank(message = "L'email est obligatoire")
  private String email;
  @NotBlank(message = "Le mot de passe est obligatoire")
  private String password;
}
