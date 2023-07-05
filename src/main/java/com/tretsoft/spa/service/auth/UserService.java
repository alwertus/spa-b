package com.tretsoft.spa.service.auth;

import com.tretsoft.spa.config.props.AppProperties;
import com.tretsoft.spa.exception.AlreadyExistsException;
import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.model.user.SpaUserStatus;
import com.tretsoft.spa.repository.user.UserRepository;
import com.tretsoft.spa.service.utility.EmailSenderService;
import com.tretsoft.spa.service.utility.RandomStringGenerator;
import com.tretsoft.spa.web.dto.SpaUserDto;
import com.tretsoft.spa.web.mapper.SpaUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SpaUserMapper spaUserMapper;
    private final EmailSenderService emailSenderService;
    private final RandomStringGenerator stringGenerator;
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public SpaUserDto create(SpaUserDto dto) {
        userRepository
                .findByLogin(dto.getLogin())
                .ifPresent(e -> {
                    throw new AlreadyExistsException(new String[] {"login=" + dto.getLogin()});
                });

        String confirmString = stringGenerator.generateString(50);

        SpaUser user = SpaUser
                .builder()
                .login(dto.getLogin())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .created(Calendar.getInstance())
                .status(SpaUserStatus.CREATED)
                .emailConfirmKey(confirmString)
                .build();
        roleService.addDefaultRoles(user);

        userRepository.saveAndFlush(user);

        SpaUserDto outDto = spaUserMapper.sourceToDto(user);

        // send email
        try {
            emailSenderService.sendSimpleMessage(user.getEmail(), "Confirmation of creating a new account on SinglePlace Assistant. " + new Date(),
                    String.format("Click here: '%s/emailConfirm/%s' to confirm registration with user: %s. Or ignore this email if you don't want",
                            appProperties.getFrontUrl(), confirmString, user.getLogin())
            );
            outDto.setMessage("Check email");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            outDto.setMessage("Cannot send email. Try to create user again later or call to administration");
            userRepository.delete(user);
        }

        return outDto;
    }

    public SpaUser getUserByLogin(String login) {
        return userRepository
                .findByLogin(login)
                .orElseThrow(() -> new BadRequestException("login='" + login + "'"));
    }

    public void updateUserLastLogin(SpaUser user) {
        user.setLastLogin(Calendar.getInstance());
        userRepository.save(user);
    }

    public void confirmEmail(String key) {
        if (key.isBlank() || key.isEmpty())
            throw new BadRequestException("key");

        SpaUser user = userRepository
                .findByEmailConfirmKey(key)
                .orElseThrow(() -> new BadRequestException("key"));

        user.setEmailConfirmKey(null);
        user.setStatus(SpaUserStatus.ACTIVE);
        user.setUpdated(Calendar.getInstance());

        userRepository.save(user);
    }

    public List<SpaUser> getAllUsers() {
        return userRepository.findAll();
    }

    public SpaUser addRoleToUser(SpaUser user, SpaRole role) {
        if (user.getRoles().contains(role)) {
            log.debug("User already has role: {}", role.getName());
            return user;
        }
        user.getRoles().add(role);
        log.debug("User after add role: {}", user);
        return userRepository.save(user);
    }

    public SpaUser removeRoleFromUser(SpaUser user, SpaRole role) {
        if (!user.getRoles().contains(role)) {
            log.debug("User not contains role: {}", role.getName());
            return user;
        }
        user.getRoles().remove(role);
        log.debug("User after remove role: {}", user);
        return userRepository.save(user);
    }

}
