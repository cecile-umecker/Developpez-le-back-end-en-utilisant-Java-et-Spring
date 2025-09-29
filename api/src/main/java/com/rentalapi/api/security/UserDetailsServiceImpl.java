package com.rentalapi.api.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rentalapi.api.model.User;
import com.rentalapi.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for loading user-specific data.
 * This class implements UserDetailsService to provide user details
 * for authentication and authorization purposes.
 * 
 * @Service Indicates that this class is a Spring service component
 * @RequiredArgsConstructor Generates a constructor with required arguments (final fields)
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                user.getPassword(), 
                new ArrayList<>() // authorities, vide pour l'instant
        );
    }
}
