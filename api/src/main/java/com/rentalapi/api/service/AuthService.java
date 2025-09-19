package com.rentalapi.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rentalapi.api.dto.AuthResponseDTO;
import com.rentalapi.api.exception.AuthException;
import com.rentalapi.api.dto.UserLoginDTO;
import com.rentalapi.api.dto.UserRegisterDTO;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.UserRepository;
import com.rentalapi.api.security.JwtService;

import lombok.RequiredArgsConstructor;

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
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        //Généreration du token JWT
        String token = jwtService.generateToken(savedUser);
        System.out.println(token); // debug
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

}
