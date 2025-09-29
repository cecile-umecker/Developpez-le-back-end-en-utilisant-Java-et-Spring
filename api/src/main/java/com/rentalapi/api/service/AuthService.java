package com.rentalapi.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rentalapi.api.dto.AuthResponseDTO;
import com.rentalapi.api.exception.AuthException;
import com.rentalapi.api.dto.UserLoginDTO;
import com.rentalapi.api.dto.UserRegisterDTO;
import com.rentalapi.api.dto.UserResponseDTO;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.UserRepository;
import com.rentalapi.api.security.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Service class for handling authentication-related operations.
 * This class provides methods for user registration, login, and retrieving
 * user information based on JWT tokens.
 * 
 * @Service Indicates that this class is a Spring service component
 * @RequiredArgsConstructor Generates a constructor with required arguments (final fields)
 */

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO register(UserRegisterDTO dto) {
        // Vérifier que l'email n'existe pas déjà
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        // Création de l'utilisateur
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        //Généreration du token JWT
        String token = jwtService.generateToken(savedUser);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(UserLoginDTO dto) {
        // Récupérer l'utilisateur en DB
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new AuthException("error"));

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException("error");
        }

        // Générer le token
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }

    public UserResponseDTO me(String token) {
        String email = jwtService.extractEmail(token);
        if(!jwtService.isTokenValid(token)) {
            throw new AuthException("Token invalide");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("Utilisateur non trouvé"));
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
