package com.rentalapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank(message = "username")
    @JsonProperty("name")
    private String username;

    @Email(message = "email")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "password")
    private String password;
}
