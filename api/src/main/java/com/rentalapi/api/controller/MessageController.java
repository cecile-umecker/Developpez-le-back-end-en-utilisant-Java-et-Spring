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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

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
