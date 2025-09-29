package com.rentalapi.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentalapi.api.model.User;

/**
 * Repository interface for managing User entities.
 * This interface extends JpaRepository to provide CRUD operations and
 * additional JPA functionalities for the User entity.
 * 
 * @Entity Indicates that this interface is a Spring Data repository
 * @Table Specifies the entity type and primary key type
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
