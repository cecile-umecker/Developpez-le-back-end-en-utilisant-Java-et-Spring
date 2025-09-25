package com.rentalapi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapi.api.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
