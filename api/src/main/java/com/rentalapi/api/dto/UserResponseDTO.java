package com.rentalapi.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
  private Integer id;

  @JsonProperty("name")
  private String name;

  private String email;

  @JsonProperty("created_at")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDateTime updatedAt;
}
