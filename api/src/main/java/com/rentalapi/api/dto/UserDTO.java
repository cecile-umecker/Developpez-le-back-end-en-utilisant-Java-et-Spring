package com.rentalapi.api.dto;

import java.time.format.DateTimeFormatter;

import com.rentalapi.api.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Data Transfer Object (DTO) for User entities.
 * This class is used to transfer user data between processes while hiding the complexity
 * of the underlying User entity.
 *
 * @Schema annotations are used to provide OpenAPI/Swagger documentation.
 * 
 * @Getter @Setter - Lombok annotations to automatically generate getters and setters
 * @NoArgsConstructor - Lombok annotation to generate a no-args constructor
 * @AllArgsConstructor - Lombok annotation to generate a constructor with all fields
 * @Builder - Lombok annotation to implement the Builder pattern
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @Schema(description = "user ID", example = "2")
    private Integer id;
    @Schema(description = "user name", example = "Owner Name")
    private String name;
    @Schema(description = "user email", example = "owner.name@example.com")
    private String email;
    @Schema(description = "user creation date", example = "2023/01/01")
    private String created_at;
    @Schema(description = "user update date", example = "2023/01/02")
    private String updated_at;

    public static UserDTO fromEntity(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created_at(user.getCreatedAt().format(formatter))
                .updated_at(user.getUpdatedAt().format(formatter))
                .build();
    }
}

