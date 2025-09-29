package com.rentalapi.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Controller for serving image files stored on the server.
 *
 * Provides endpoints to retrieve images by filename and handles CORS headers explicitly
 * to allow cross-origin requests. The image files are served from the directory specified
 * by the 'app.upload.dir' property, defaulting to "uploads" if not set.
 *
 * Endpoints:
 *   - GET /images/{filename} : Retrieves the image file with the specified filename.
 *     Returns the image as a org.springframework.core.io.Resource with appropriate
 *     content type and CORS headers. If the file does not exist or is not readable,
 *     returns 404 Not Found.
 *   - OPTIONS /images/{filename} : Handles preflight CORS requests for image retrieval.
 *     Returns the necessary CORS headers.
 *
 * CORS headers included:
 *   - Access-Control-Allow-Origin: *
 *   - Access-Control-Allow-Methods: GET, OPTIONS
 *   - Access-Control-Allow-Headers: *
 *   - Cross-Origin-Resource-Policy: cross-origin
 *   - Cross-Origin-Embedder-Policy: unsafe-none
 */

@RestController
@RequestMapping("/images")
public class ImageController {
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    @Hidden
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename, HttpServletRequest request) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                
                return ResponseEntity.ok()
                        // Headers CORS explicites pour éviter ORB
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, OPTIONS")
                        .header("Access-Control-Allow-Headers", "*")
                        .header("Cross-Origin-Resource-Policy", "cross-origin")
                        .header("Cross-Origin-Embedder-Policy", "unsafe-none")
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Hidden
    @RequestMapping(value = "/{filename:.+}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, OPTIONS")
                .header("Access-Control-Allow-Headers", "*")
                .build();
    }
}