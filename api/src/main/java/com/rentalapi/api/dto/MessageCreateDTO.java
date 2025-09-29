package com.rentalapi.api.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for creating message entities.
 * This class is used to transfer message data from client to server during message creation.
 *
 * The class includes:
 * @field message - The content of the message
 * @field user_id - The ID of the user sending the message
 * @field rental_id - The ID of the rental associated with the message
 */

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

