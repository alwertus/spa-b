package com.tretsoft.spa.service;

import com.tretsoft.spa.config.AppProperties;
import com.tretsoft.spa.exception.AlreadyExistsException;
import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.mapper.SpaUserMapper;
import com.tretsoft.spa.model.domain.SpaUser;
import com.tretsoft.spa.model.domain.SpaUserStatus;
import com.tretsoft.spa.model.dto.SpaUserDto;
import com.tretsoft.spa.repository.UserRepository;
import com.tretsoft.spa.service.utility.EmailSenderService;
import com.tretsoft.spa.service.utility.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

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

        userRepository.saveAndFlush(user);

        SpaUserDto outDto = spaUserMapper.sourceToDto(user);

        // send email
        try {
            emailSenderService.sendSimpleMessage(user.getEmail(), "Confirmation of creating a new account on SinglePlace Assistent. " + new Date(),
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

    public void confirmEmail(String key) {
        if (key.isBlank() || key.isEmpty())
            throw new BadRequestException(new String[] {"key"});

        SpaUser user = userRepository
                .findByEmailConfirmKey(key)
                .orElseThrow(() -> new BadRequestException(new String[] {"key"}));

        user.setEmailConfirmKey(null);
        user.setStatus(SpaUserStatus.ACTIVE);
        user.setUpdated(Calendar.getInstance());

        userRepository.save(user);
    }

}
