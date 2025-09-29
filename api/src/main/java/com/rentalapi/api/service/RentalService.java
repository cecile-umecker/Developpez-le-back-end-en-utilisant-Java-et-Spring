package com.rentalapi.api.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rentalapi.api.dto.RentalCreateRequestDTO;
import com.rentalapi.api.dto.RentalDTO;
import com.rentalapi.api.dto.RentalSummaryDTO;
import com.rentalapi.api.dto.RentalUpdateRequestDTO;
import com.rentalapi.api.model.Rental;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.RentalRepository;
import com.rentalapi.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for handling rental-related operations.
 * This class provides methods for creating, updating, and retrieving rental information.
 * 
 * @Service Indicates that this class is a Spring service component
 * @RequiredArgsConstructor Generates a constructor with required arguments (final fields)
 */

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public List<RentalSummaryDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(RentalSummaryDTO::fromEntity)
                .toList();
    }

    public RentalDTO createRental(RentalCreateRequestDTO request, UserDetails userDetails) throws IOException {
        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String pictureUrl = fileStorageService.storeFile(request.getPicture());

        Rental rental = Rental.builder()
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .description(request.getDescription())
                .picture(pictureUrl)
                .owner_id(owner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        rentalRepository.save(rental);

        return RentalDTO.fromEntity(rental);
    }

    public void updateRental(Integer id, RentalUpdateRequestDTO request, UserDetails userDetails) {
        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (!rental.getOwner_id().getId().equals(owner.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // On met à jour uniquement les champs éditables depuis le front
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        rentalRepository.save(rental);
    }

    public Optional<RentalDTO> getRentalById(Integer id) {
        return rentalRepository.findById(id).map(RentalDTO::fromEntity);
    }



}
