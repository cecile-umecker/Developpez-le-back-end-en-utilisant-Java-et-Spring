package com.rentalapi.api.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Data Transfer Object representing an incoming rental creation request.
 * This class encapsulates all the data needed to create a new rental listing.
 *
 * Fields:
 * @field name The name/title of the rental property
 * @field surface The surface area of the rental property in square meters
 * @field price The monthly rental price in the local currency
 * @field description A detailed description of the rental property
 * @field picture An uploaded image file representing the rental property
 * 
 * The class uses Lombok annotations to automatically generate:
 * - Getters and setters (@Data)
 * - Builder pattern methods (@Builder)
 * - No-args constructor (@NoArgsConstructor)
 * - All-args constructor (@AllArgsConstructor)
 * 
 * Swagger annotations (@Schema) are used to provide API documentation
 * and examples for each field.
 *
 * Contains the rental name, surface area, price, description, and picture.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalCreateRequestDTO {
    @Schema(description = "rental name", example = "Dream house")
    private String name;
    @Schema(description = "surface", example = "45")
    private Float surface;
    @Schema(description = "price", example = "950")
    private Float price;
    @Schema(description = "description", example = "Appartement lumineux avec balcon")
    private String description;
    @Schema(description = "picture", type = "string", format = "binary")
    private MultipartFile picture;
}
