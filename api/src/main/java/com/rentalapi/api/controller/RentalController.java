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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rentalapi.api.dto.RentalCreateDTO;
import com.rentalapi.api.dto.RentalDTO;
import com.rentalapi.api.dto.RentalSummaryDTO;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.UserRepository;
import com.rentalapi.api.service.FileStorageService;
import com.rentalapi.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

  private final RentalService rentalService;
  private final UserRepository userRepository;
  private final FileStorageService fileStorageService;

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
          @RequestParam String name,
          @RequestParam Float surface,
          @RequestParam Float price,
          @RequestParam String description,
          @RequestParam("picture") MultipartFile pictureFile,
          @AuthenticationPrincipal UserDetails userDetails
  ) throws IOException {

      // récupérer l'utilisateur connecté
      User owner = userRepository.findByEmail(userDetails.getUsername())
              .orElseThrow(() -> new RuntimeException("User not found"));

      // traitement du fichier : ex. stockage et récupération de l'URL
      String pictureUrl = fileStorageService.storeFile(pictureFile);

      // créer DTO pour le service
      RentalCreateDTO dto = RentalCreateDTO.builder()
              .name(name)
              .surface(surface)
              .price(price)
              .description(description)
              .build();
      
      rentalService.createRental(dto, owner, pictureUrl);

      Map<String, String> response = new HashMap<>();
      response.put("message", "Rental created !");

      return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
          @RequestParam String name,
          @RequestParam Float surface,
          @RequestParam Float price,
          @RequestParam String description,
          @RequestParam(value = "picture", required = false) MultipartFile pictureFile,
          @AuthenticationPrincipal UserDetails userDetails
  ) throws IOException {

      User owner = userRepository.findByEmail(userDetails.getUsername())
              .orElseThrow(() -> new RuntimeException("User not found"));

      String pictureUrl = null;
      if (pictureFile != null && !pictureFile.isEmpty()) {
          pictureUrl = fileStorageService.storeFile(pictureFile);
      }

      rentalService.updateRental(id, owner, name, surface, price, description, pictureUrl);

      Map<String, String> response = new HashMap<>();
      response.put("message", "Rental updated !");
      return ResponseEntity.ok(response);
  }




}
