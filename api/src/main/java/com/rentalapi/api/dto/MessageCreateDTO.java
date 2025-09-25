package com.rentalapi.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageCreateDTO {
    private String message;
    private Integer user_id;
    private Integer rental_id;
}

