package com.rentalapi.api.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentalapi.api.dto.MessageCreateDTO;
import com.rentalapi.api.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @Operation(summary = "Send message to owner",
    parameters = {
            @Parameter(
                name = "Authorization",
                description = "Bearer token (format: 'Bearer <JWT>')",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
            )
        })
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Rental created",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(example = "{ \"message\": \"Message send with success\" }")
                )
        ),
        @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Missing or invalid token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)))
    })
  @PostMapping
  public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageCreateDTO dto) {
    try {
            messageService.sendMessage(dto);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Message send with success");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyMap());
        }
  }

}
