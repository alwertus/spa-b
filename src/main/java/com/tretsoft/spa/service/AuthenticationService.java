package com.tretsoft.spa.service;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.EmailNotConfirmedException;
import com.tretsoft.spa.exception.WrongStatusException;
import com.tretsoft.spa.web.mapper.SpaUserMapper;
import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.model.SpaUserStatus;
import com.tretsoft.spa.web.dto.UserLoginDto;
import com.tretsoft.spa.service.utility.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SpaUserMapper spaUserMapper;
    private final TokenService tokenService;


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

        //TODO: check request url
        return new UserLoginDto(
                spaUserMapper.sourceToDto(user),
                tokenService.generateToken(user.getLogin(), /*request.getRequestURL().toString(),*/ user.getRoles(), false),
                tokenService.generateToken(user.getLogin(), /*request.getRequestURL().toString(),*/ user.getRoles(), true)
        );
    }

//    TODO: DO IT
    public SpaUser verifyToken(String token) {
        return null;
    }

    public UserLoginDto updateTokens(String refreshToken) {
//        if ()
        return null;
    }

}
