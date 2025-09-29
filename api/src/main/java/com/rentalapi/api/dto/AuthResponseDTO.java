package com.rentalapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for authentication response.
 * This class represents the response sent back to the client after a successful authentication.
 * It contains a JWT (JSON Web Token) that can be used for subsequent authenticated requests.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    @Schema(description = "user JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
