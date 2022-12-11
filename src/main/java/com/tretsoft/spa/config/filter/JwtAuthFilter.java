package com.tretsoft.spa.config.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tretsoft.spa.config.props.JwtProperties;
import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.SimpleException;
import com.tretsoft.spa.service.AuthenticationService;
import com.tretsoft.spa.web.dto.ResponseError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final AuthenticationService authenticationService;

    private void writeErrorMessage(HttpServletResponse rs, Exception e) {
        try {
            log.error(e);
            rs.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            new ObjectMapper().writeValue(rs.getOutputStream(), new ResponseError(new SimpleException(e.getMessage())));
        } catch (Exception ex) {
            log.error("Cannot write error message to output stream", ex);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest rq, @NonNull HttpServletResponse rs, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // get Authorization header
        final String authHeader = rq.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(jwtProperties.getTokenPrefix())) {
            filterChain.doFilter(rq, rs);
            return;
        }

        String token = authHeader.substring(jwtProperties.getTokenPrefix().length());

        try {
            User user = authenticationService.convertTokenToUser(token);
            authenticationService.authenticate(user);
        } catch (TokenExpiredException | JWTDecodeException ex) {
            writeErrorMessage(rs, ex);
        } catch (BadRequestException ex) {
            writeErrorMessage(rs, new UsernameNotFoundException(ex.getMessage()));
        } catch (Exception e) {
            log.error("Unknown error: {}", e.getMessage());
            e.printStackTrace();
            writeErrorMessage(rs, e);
        }

        // go to next filter
        filterChain.doFilter(rq, rs);
    }

}
