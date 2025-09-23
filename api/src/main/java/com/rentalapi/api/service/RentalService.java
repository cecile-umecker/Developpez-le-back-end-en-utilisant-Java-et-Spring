package com.rentalapi.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rentalapi.api.dto.RentalCreateDTO;
import com.rentalapi.api.dto.RentalDTO;
import com.rentalapi.api.dto.RentalSummaryDTO;
import com.rentalapi.api.model.Rental;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.RentalRepository;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<RentalSummaryDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(RentalSummaryDTO::fromEntity)
                .toList();
    }

    public RentalDTO createRental(RentalCreateDTO dto, User owner, String pictureUrl) {
        Rental rental = Rental.builder()
                .name(dto.getName())
                .surface(dto.getSurface())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .picture(pictureUrl)
                .owner_id(owner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        rentalRepository.save(rental);
        return RentalDTO.fromEntity(rental);
    }

    public Optional<RentalDTO> getRentalById(Integer id) {
        return rentalRepository.findById(id).map(RentalDTO::fromEntity);
    }

    public void updateRental(Integer id, User owner, String name, Float surface, Float price, String description, String pictureUrl) {

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Vérification propriétaire (optionnel)
        if (!rental.getOwner_id().getId().equals(owner.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        if (pictureUrl != null) {
            rental.setPicture(pictureUrl);
        }
        rental.setUpdatedAt(LocalDateTime.now());

        rentalRepository.save(rental);
    }



}
