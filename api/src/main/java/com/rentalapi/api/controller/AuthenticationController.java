package com.rentalapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rentalapi.api.dto.AuthResponseDTO;
import com.rentalapi.api.dto.ErrorResponseDTO;
import com.rentalapi.api.dto.UserLoginDTO;
import com.rentalapi.api.dto.UserRegisterDTO;
import com.rentalapi.api.exception.AuthException;
import com.rentalapi.api.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        AuthResponseDTO response = authService.register(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO dto) {
        try {
            AuthResponseDTO response = authService.login(dto);
            return ResponseEntity.ok(response);
        } catch (AuthException e) {
            // utiliser le message de l'exception directement
            return ResponseEntity.status(401).body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
