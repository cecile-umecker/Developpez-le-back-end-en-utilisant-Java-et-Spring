package com.rentalapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing an error response.
 * This class is used to encapsulate error messages that are sent back to the client.
 * It uses Lombok annotations to automatically generate getters, setters, and constructors.
 * 
 * @Schema annotations provide OpenAPI/Swagger documentation details.
 */

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
  @Schema(description = "Error message", example = "error")
  private String message;
}
