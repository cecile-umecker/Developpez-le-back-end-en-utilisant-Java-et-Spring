package com.rentalapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalUpdateRequestDTO {
    @Schema(description = "rental name", example = "Dream house")
    private String name;
    @Schema(description = "surface", example = "45")
    private Float surface;
    @Schema(description = "price", example = "950")
    private Float price;
    @Schema(description = "description", example = "Appartement lumineux avec balcon")
    private String description;
}

