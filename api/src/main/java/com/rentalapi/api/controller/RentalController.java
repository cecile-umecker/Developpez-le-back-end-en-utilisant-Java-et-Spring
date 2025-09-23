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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

  private final RentalService rentalService;
  private final UserRepository userRepository;
  private final FileStorageService fileStorageService;

  @GetMapping
  public ResponseEntity<Map<String, List<RentalSummaryDTO>>> getAllRentals() {
    Map<String, List<RentalSummaryDTO>> response = new HashMap<>();
    response.put("rentals", rentalService.getAllRentals());
    return ResponseEntity.ok(response);
  }

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

  @GetMapping("/{id}")
  public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
      RentalDTO rentalDTO = rentalService.getRentalById(id)
              .orElseThrow(() -> new RuntimeException("Rental not found")); // ou custom exception 404
      return ResponseEntity.ok(rentalDTO);
  }

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
