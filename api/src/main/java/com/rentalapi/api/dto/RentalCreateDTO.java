
package com.rentalapi.api.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for creating a new rental.
 * This class encapsulates the data required to create a rental entity.
 *
 * @property name The name/title of the rental property
 * @property surface The surface area of the rental property in square meters
 * @property price The rental price per period (typically per month)
 * @property description A detailed description of the rental property
 */

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
