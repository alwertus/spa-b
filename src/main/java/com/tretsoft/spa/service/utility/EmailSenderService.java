package com.tretsoft.spa.service.utility;

import com.tretsoft.spa.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final AppProperties appProperties;

    public void sendSimpleMessage(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        if (!appProperties.getMockEmailSending())
            mailSender.send(message);

        log.info(String.format("%sEmail to %s send successfully. Body=\n%s",
                appProperties.getMockEmailSending() ? "MOCK!!! " : "",
                email,
                text));
    }

}
