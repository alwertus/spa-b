package com.tretsoft.spa.service.utility;

import com.auth0.jwt.JWT;
import com.tretsoft.spa.config.props.JwtProperties;
import com.tretsoft.spa.model.domain.SpaRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProperties jwtProperties;

//    TODO: clear comments below
    public String generateToken(String userName, /*String issuer,*/ List<SpaRole> userRoles, boolean isRefreshToken) {
//        StringBuilder sb = new StringBuilder(String.format("Generate token with parameters: userName='%s', issuer='%s', isRefreshToken='%s' => ", userName, issuer, isRefreshToken));
        StringBuilder sb = new StringBuilder(String.format("Generate token with parameters: userName='%s', isRefreshToken='%s' => ", userName, isRefreshToken));
        try {
            Date expireDate = new Date(System.currentTimeMillis() + (long) (isRefreshToken ? 100 : 1) * jwtProperties.getTokenExpiration() * 60 * 1000);

            List<String> rolesList = userRoles.stream()
                    .map(SpaRole::getName)
                    .collect(Collectors.toList());

            String token = jwtProperties.getTokenPrefix() + JWT
                    .create()
                    .withSubject(userName)
                    .withExpiresAt(expireDate)
//                            .withIssuer(issuer)
                    .withClaim("roles", rolesList)
                    .sign(jwtProperties.getAlgorithm());

            log.trace(sb.append("Success"));
            return token;
        } catch (Exception e) {
            log.error(sb.append("Error: ").append(e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
    }
}
