package com.rentalapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rentalapi.api.dto.UserDTO;
import com.rentalapi.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get owner information",
        parameters = {
            @Parameter(
                name = "Authorization",
                description = "Bearer token (format: 'Bearer <JWT>')",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
            )
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

