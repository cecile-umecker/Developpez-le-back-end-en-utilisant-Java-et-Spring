package com.rentalapi.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalapi.api.model.Message;

/**
 * Repository interface for managing Message entities.
 * This interface extends JpaRepository to provide CRUD operations and
 * additional JPA functionalities for the Message entity.
 * 
 * @Entity Indicates that this interface is a Spring Data repository
 * @Table Specifies the entity type and primary key type
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
