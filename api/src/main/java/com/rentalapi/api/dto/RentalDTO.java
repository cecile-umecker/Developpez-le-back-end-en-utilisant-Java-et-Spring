package com.rentalapi.api.dto;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.rentalapi.api.model.Rental;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDTO {

    private Integer id;
    private String name;
    private Float surface;
    private Float price;
    private List<String> picture;
    private String description;
    private Integer owner_id;
    private String created_at;
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
