package com.rentalapi.api.service;

import org.springframework.stereotype.Service;

import com.rentalapi.api.dto.UserDTO;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;

/**
 * Service class for handling user-related operations.
 * This class provides methods for retrieving user information.
 * 
 * @Service Indicates that this class is a Spring service component
 * @RequiredArgsConstructor Generates a constructor with required arguments (final fields)
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserDTO.fromEntity(user);
    }
}

