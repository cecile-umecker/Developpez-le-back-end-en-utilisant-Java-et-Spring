package com.rentalapi.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalCreateDTO {
    private String name;
    private Float surface;
    private Float price;
    private String description;
}
