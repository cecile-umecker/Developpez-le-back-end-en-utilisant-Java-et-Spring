package com.rentalapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration.
 * This class represents the data structure used to transfer user registration information.
 * 
 * @property name     The username of the registering user. Cannot be blank.
 * @property email    The email address of the registering user. Must be a valid email format and cannot be blank.
 * @property password The password for the user account. Cannot be blank.
 */

@Data
public class UserRegisterDTO {
    @NotBlank(message = "username")
    @JsonProperty("name")
    private String name;

    @Email(message = "email")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "password")
    private String password;
}
