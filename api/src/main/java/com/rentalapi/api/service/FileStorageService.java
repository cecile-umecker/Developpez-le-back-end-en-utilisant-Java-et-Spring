package com.rentalapi.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }
    
    public String storeFile(MultipartFile file) {
        try {
            String filename = UUID.randomUUID().toString() + "_" + 
                            StringUtils.cleanPath(file.getOriginalFilename());
            
            Path targetLocation = Paths.get(uploadDir).resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            // Retourner l'URL complète
            return "http://localhost:8080/api/images/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
}