package com.tretsoft.spa.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest rq, HttpServletResponse rs, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = rq.getHeader(HttpHeaders.AUTHORIZATION);

    }

}
