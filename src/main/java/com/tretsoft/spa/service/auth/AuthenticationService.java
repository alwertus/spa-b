package com.tretsoft.spa.service.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tretsoft.spa.config.props.JwtProperties;
import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.EmailNotConfirmedException;
import com.tretsoft.spa.exception.WrongStatusException;
import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.model.SpaUserStatus;
import com.tretsoft.spa.web.dto.UserLoginDto;
import com.tretsoft.spa.web.mapper.SpaUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SpaUserMapper spaUserMapper;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    private UserLoginDto generateTokens(SpaUser user) {
        return new UserLoginDto(
                spaUserMapper.sourceToDto(user),
                tokenService.generateToken(user.getLogin(), user.getRoles(), false),
                tokenService.generateToken(user.getLogin(), user.getRoles(), true)
        );
    }

    @Transactional
    public UserLoginDto signInByLoginAndPassword(String login, String password) {
        SpaUser user = userService.getUserByLogin(login);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadRequestException("password");

        if (user.getEmailConfirmKey() != null)
            throw new EmailNotConfirmedException(user.getLogin(), new String[] {user.getEmail()});

        if (user.getStatus() != SpaUserStatus.ACTIVE)
            throw new WrongStatusException(user.getStatus().toString());

        userService.updateUserLastLogin(user);

        return generateTokens(user);
    }

    public UserDetails findUserByToken(String token) {
        // decode & verify expire date token
        DecodedJWT decodedJWT = jwtProperties
                .getVerifier()
                .verify(token);

        // get username from token
        String username = decodedJWT.getSubject();
        return userService.getUserByLogin(username);
    }

    public UserLoginDto updateTokens() {
        return generateTokens(getCurrentUser());
    }

    public SpaUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByLogin(authentication.getName());
    }

}
