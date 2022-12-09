package com.tretsoft.spa.config.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tretsoft.spa.config.props.JwtProperties;
import com.tretsoft.spa.exception.SimpleException;
import com.tretsoft.spa.service.UserService;
import com.tretsoft.spa.web.dto.ResponseError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final UserService userService;

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

        // decode & verify expire date token
        String token = authHeader.substring(jwtProperties.getTokenPrefix().length());
        DecodedJWT decodedJWT;
        try {
             decodedJWT = jwtProperties.getVerifier().verify(token);
        } catch (TokenExpiredException | JWTDecodeException ex) {
            writeErrorMessage(rs, ex);
            return;
        }

        // get roles from token
        String username = decodedJWT.getSubject();
        List<SimpleGrantedAuthority> roles = Arrays.stream(
                decodedJWT
                        .getClaim("roles")
                        .asArray(SimpleGrantedAuthority.class)
        ).toList();

        // check user exists
        try {
            userService.getUserByLogin(username);
        } catch (Exception ex) {
            writeErrorMessage(rs, new UsernameNotFoundException(ex.getMessage()));
            return;
        }

        // set authentication
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // go to next filter
        filterChain.doFilter(rq, rs);
    }

}
