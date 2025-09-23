package com.rentalapi.api.dto;

import java.time.format.DateTimeFormatter;

import com.rentalapi.api.model.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String created_at;
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

