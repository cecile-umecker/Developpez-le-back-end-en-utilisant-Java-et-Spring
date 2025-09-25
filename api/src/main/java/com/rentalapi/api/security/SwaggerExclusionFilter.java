package com.rentalapi.api.security;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@Component
@Order(1)
public class SwaggerExclusionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/v3/api-docs") || path.startsWith("/api/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

