package com.rentalapi.api.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rentalapi.api.dto.AuthResponseDTO;
import com.rentalapi.api.dto.ErrorResponseDTO;
import com.rentalapi.api.dto.UserLoginDTO;
import com.rentalapi.api.dto.UserRegisterDTO;
import com.rentalapi.api.dto.UserResponseDTO;
import com.rentalapi.api.exception.AuthException;
import com.rentalapi.api.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.media.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @Operation(summary = "Create new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        AuthResponseDTO response = authService.register(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Invalid email or password",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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

    @Operation(summary = "Get current user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token")
    })
    @GetMapping("/me")
    public ResponseEntity<?> me(@Parameter(description = "User JWT token", required = true)@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(new HashMap<>());
            }

            String token = authHeader.substring(7);
            UserResponseDTO response = authService.me(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new HashMap<>());
        }
    }
}
