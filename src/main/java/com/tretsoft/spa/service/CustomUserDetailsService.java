package com.tretsoft.spa.service;

import com.tretsoft.spa.model.SpaUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Find user {}", username);
        SpaUser spaUser = userService.getUserByLogin(username);
        return new User(
                spaUser.getLogin(),
                spaUser.getPassword(),
                spaUser
                        .getRoles()
                        .stream()
                        .map(e -> new SimpleGrantedAuthority(e.getName()))
                        .collect(Collectors.toList()));
    }

}
