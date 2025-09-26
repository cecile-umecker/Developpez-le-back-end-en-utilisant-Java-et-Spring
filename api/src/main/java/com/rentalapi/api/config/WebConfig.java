package com.rentalapi.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for customizing Spring MVC settings.
 *
 * This class enables Web MVC and provides custom configurations for:
 * - Serving static resources from the 'uploads' directory under the '/uploads/**' URL pattern.
 * - Setting cache period for static resources to 1 hour.
 * - Configuring Cross-Origin Resource Sharing (CORS) to allow all origins, all headers,
 *   and common HTTP methods with credentials support.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configuration to serve files from the uploads folder
        registry.addResourceHandler("/uploads/**")
                // Path relative to the project root
                .addResourceLocations("file:uploads/")
                .setCachePeriod(3600) // 1hour cache
                .resourceChain(true);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
