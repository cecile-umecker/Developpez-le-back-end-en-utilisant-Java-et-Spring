package com.rentalapi.api.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.UserRepository;
import com.rentalapi.api.security.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Hachage du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Génération du token JWT
        return jwtService.generateToken(user.getEmail());
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Vérification du mot de passe
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Génération du token JWT
        return jwtService.generateToken(existingUser.getEmail());
    }
}
