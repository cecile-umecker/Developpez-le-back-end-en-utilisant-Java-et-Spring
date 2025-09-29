package com.rentalapi.api.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentalapi.api.dto.RentalCreateRequestDTO;
import com.rentalapi.api.dto.RentalDTO;
import com.rentalapi.api.dto.RentalSummaryDTO;
import com.rentalapi.api.dto.RentalUpdateRequestDTO;

import com.rentalapi.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

/*
 * Controller for handling rental-related endpoints such as creating, updating, 
 * retrieving a list of rentals, and retrieving details of a specific rental.
 *
 * Endpoints:
 *   - GET /rentals: Retrieve a list of all rentals.
 *   - POST /rentals: Create a new rental (with picture upload via multipart/form-data).
 *   - GET /rentals/{id}: Retrieve detailed information about a specific rental by ID.
 *   - PUT /rentals/{id}: Update an existing rental's information (excluding picture).
 *
 * Uses RentalService for business logic related to rentals.
 */

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

  private final RentalService rentalService;


  @Operation(summary = "Get rentals list",
        parameters = {
            @Parameter(
                name = "Authorization",
                description = "Bearer token (format: 'Bearer <JWT>')",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
            )
        })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"rentals\": [ { \"id\": 1, \"name\": \"test house 1\", \"surface\": 432, \"price\": 300, \"picture\": \"https://...\", \"description\": \"...\", \"owner_id\": 1, \"created_at\": \"2012/12/02\", \"updated_at\": \"2014/12/02\" } ] }"
            ))),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token",
                    content = @Content(mediaType = "application/json"))
    })
  @GetMapping
  public ResponseEntity<Map<String, List<RentalSummaryDTO>>> getAllRentals() {
    Map<String, List<RentalSummaryDTO>> response = new HashMap<>();
    response.put("rentals", rentalService.getAllRentals());
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Create new rental",
        parameters = {
            @Parameter(
                name = "Authorization",
                description = "Bearer token (format: 'Bearer <JWT>')",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
            )
        })
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Rental created",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(example = "{ \"message\": \"Rental created !\" }")
                )
        ),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> createRental(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Rental info",
                required = true,
                content = @Content(schema = @Schema(implementation = RentalCreateRequestDTO.class),
                            mediaType = "multipart/form-data")
            )            
            @ModelAttribute RentalCreateRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {

    rentalService.createRental(request, userDetails);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("message", "Rental created !"));
    }

  @Operation(summary = "Get rental information",
        parameters = {
            @Parameter(
                name = "Authorization",
                description = "Bearer token (format: 'Bearer <JWT>')",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
            )
        })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token",
                    content = @Content(mediaType = "application/json"))
    })
  @GetMapping("/{id}")
  public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
      RentalDTO rentalDTO = rentalService.getRentalById(id)
              .orElseThrow(() -> new RuntimeException("Rental not found")); // ou custom exception 404
      return ResponseEntity.ok(rentalDTO);
  }

  @Operation(summary = "Update rental",
        parameters = {
            @Parameter(
                name = "Authorization",
                description = "Bearer token (format: 'Bearer <JWT>')",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
            )
        })
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Rental created",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(example = "{ \"message\": \"Rental updated !\" }")
                )
        ),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token",
                    content = @Content(mediaType = "application/json"))
    })
  @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> updateRental(
        @PathVariable Integer id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Rental info",
            required = true,
            content = @Content(schema = @Schema(implementation = RentalUpdateRequestDTO.class))
        )        @ModelAttribute RentalUpdateRequestDTO request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        rentalService.updateRental(id, request, userDetails);
        return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    }
}
