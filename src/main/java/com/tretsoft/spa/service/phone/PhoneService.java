package com.tretsoft.spa.service.phone;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.exception.SimpleException;
import com.tretsoft.spa.model.phone.Modem;
import com.tretsoft.spa.model.phone.UserPhoneConfig;
import com.tretsoft.spa.model.phone.Sms;
import com.tretsoft.spa.repository.PhoneConfigRepository;
import com.tretsoft.spa.repository.PhoneRepository;
import com.tretsoft.spa.repository.SmsRepository;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final AuthenticationService authenticationService;
    private final SmsRepository smsRepository;
    private final PhoneConfigRepository phoneConfigRepository;
    private final PhoneRepository phoneRepository;

    public List<Modem> getAllModems() {
        return phoneRepository.findAll();
    }

    public List<Sms> getSms() {
        return smsRepository.findByModemId(getModemForCurrentUser().getId());
    }

    public void markSmsAsRead(Long smsId) {
        Sms sms = smsRepository.findById(smsId)
                .orElseThrow(() -> new BadRequestException("id"));

        if (!sms.getModem().equals(getModemForCurrentUser()))
            throw new ForbiddenException("SMS id=" + smsId);

        sms.setRead(true);
        smsRepository.save(sms);
    }

    public void deleteSms(Long smsId) {
        Sms sms = smsRepository.findById(smsId)
                .orElseThrow(() -> new BadRequestException("id"));

        if (!sms.getModem().equals(getModemForCurrentUser()))
            throw new ForbiddenException("SMS id=" + smsId);

        smsRepository.delete(sms);
    }

    public Modem getModemForCurrentUser() {
        UserPhoneConfig phoneConfig = phoneConfigRepository
                .findById(authenticationService.getCurrentUser().getId())
                .orElseThrow(() -> new SimpleException("Phone not configured for this user"));
        return phoneConfig.getModem();
    }

    public void sendSms(String phoneNumber, String smsText) {
        Sms sms = Sms.builder()
                .modem(getModemForCurrentUser())
                .sender(phoneNumber)
                .message(smsText)
                .created(Calendar.getInstance())
                .direction("OUT")
                .nature("SMS")
                .read(true)
                .build();
        smsRepository.save(sms);
    }

}