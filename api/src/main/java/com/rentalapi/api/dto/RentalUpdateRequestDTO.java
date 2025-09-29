package com.rentalapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Data Transfer Object (DTO) for rental update requests.
 * This class represents the data structure used to update rental information.
 * 
 * @Schema annotations provide OpenAPI/Swagger documentation for each field
 * @Data Lombok annotation generates getters, setters, toString, equals, and hashCode methods
 * @Builder Lombok annotation implements the Builder pattern
 * @NoArgsConstructor Lombok annotation generates a no-args constructor
 * @AllArgsConstructor Lombok annotation generates a constructor with all arguments
 */

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

