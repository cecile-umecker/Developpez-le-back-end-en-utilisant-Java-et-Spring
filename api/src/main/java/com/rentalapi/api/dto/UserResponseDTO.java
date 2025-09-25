package com.rentalapi.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
  @Schema(description = "user ID", example = "1")
  private Integer id;

  @Schema(description = "user full name", example = "John Doe")
  @JsonProperty("name")
  
  private String name;

  @Schema(description = "user email address", example = "john.doe@example.com")
  private String email;

  @JsonProperty("created_at")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @Schema(description = "user account creation date", example = "2022/02/02")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @Schema(description = "user account update date", example = "2022/02/02")
  private LocalDateTime updatedAt;
}
