package com.rentalapi.api.dto;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.rentalapi.api.model.Rental;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Data Transfer Object (DTO) for Rental entity.
 * Represents rental property details for API responses.
 *
 * @property id Unique identifier of the rental
 * @property name Name of the rental property
 * @property surface Surface area of the rental (in square meters)
 * @property price Price of the rental
 * @property picture List of URLs pointing to rental property images
 * @property description Description of the rental property
 * @property owner_id Unique identifier of the rental owner
 * @property created_at Creation date of the rental record (formatted as yyyy/MM/dd)
 * @property updated_at Last update date of the rental record (formatted as yyyy/MM/dd)
 *
 * Provides a static method fromEntity(Rental rental) to convert a Rental entity to RentalDTO.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDTO {

    @Schema(description = "rental ID", example = "1")
    private Integer id;
    @Schema(description = "rental name", example = "Cozy Apartment")
    private String name;
    @Schema(description = "rental surface", example = "52.0")
    private Float surface;
    @Schema(description = "rental price", example = "100.0")
    private Float price;
    @Schema(description = "rental picture URL list", example = "['.../images/rental1.jpg', '.../images/rental2.jpg']")
    private List<String> picture;
    @Schema(description = "rental description", example = "A cozy apartment in the city center.")
    private String description;
    @Schema(description = "rental owner ID", example = "2")
    private Integer owner_id;
    @Schema(description = "rental creation date", example = "2023/01/01")
    private String created_at;
    @Schema(description = "rental update date", example = "2023/01/02")
    private String updated_at;

    public static RentalDTO fromEntity(Rental rental) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        return RentalDTO.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(Collections.singletonList(rental.getPicture()))
                .description(rental.getDescription())
                .owner_id(rental.getOwner_id().getId())
                .created_at(rental.getCreatedAt().format(formatter))
                .updated_at(rental.getUpdatedAt().format(formatter))
                .build();
    }
}
