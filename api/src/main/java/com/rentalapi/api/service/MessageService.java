package com.rentalapi.api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rentalapi.api.dto.MessageCreateDTO;
import com.rentalapi.api.model.Message;
import com.rentalapi.api.model.Rental;
import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.MessageRepository;
import com.rentalapi.api.repository.RentalRepository;
import com.rentalapi.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final UserRepository userRepository;
  private final RentalRepository rentalRepository;
  private final MessageRepository messageRepository;

  public void sendMessage(MessageCreateDTO dto) {
    if(dto.getMessage() == null || dto.getMessage().isBlank() || dto.getUser_id() == null || dto.getRental_id() == null) {
      throw new IllegalArgumentException("Invalid message request");
    }

    User user = userRepository.findById(dto.getUser_id())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    Rental rental = rentalRepository.findById(dto.getRental_id())
        .orElseThrow(() -> new IllegalArgumentException("Rental not found"));

    Message message = Message.builder()
        .message(dto.getMessage())
        .user_id(user)
        .rental_id(rental)
        .createdAt(LocalDateTime.now())
        .build();

    messageRepository.save(message);
  }
}
