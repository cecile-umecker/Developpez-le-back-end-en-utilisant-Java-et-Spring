package com.rentalapi.api.dto;

import java.time.format.DateTimeFormatter;

import com.rentalapi.api.model.Rental;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalSummaryDTO {
    @Schema(description = "rental ID", example = "1")
    private Integer id;
    @Schema(description = "rental name", example = "Cozy Apartment")
    private String name;
    @Schema(description = "rental surface", example = "75.0")
    private Float surface;
    @Schema(description = "rental price", example = "1200.0")
    private Float price;
    @Schema(description = "rental picture URL", example = ".../images/rental1.jpg")
    private String picture;
    @Schema(description = "rental description", example = "A cozy apartment in the city center.")
    private String description;
    @Schema(description = "rental owner ID", example = "2")
    private Integer owner_id;
    @Schema(description = "rental creation date", example = "2023/01/01")
    private String created_at;
    @Schema(description = "rental update date", example = "2023/01/02")
    private String updated_at;

    public static RentalSummaryDTO fromEntity(Rental rental) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        return RentalSummaryDTO.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture()) // simple String
                .description(rental.getDescription())
                .owner_id(rental.getOwner_id().getId())
                .created_at(rental.getCreatedAt().format(formatter))
                .updated_at(rental.getUpdatedAt().format(formatter))
                .build();
    }
}
