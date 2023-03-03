package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.service.phone.PhoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@Tag(name = "Phone", description = "Access to 3g modem")
public class PhoneController extends ExceptionHandlerController {
    private final PhoneService phoneService;

    private record SmsDto(Long id, Long created, String number, String message, Boolean read, String direction){}

    @GetMapping("/sms")
    public List<SmsDto> getSms() {
        return phoneService
                .getSms()
                .stream()
                .map(e -> new SmsDto(
                        e.getId(),
                        e.getCreated() == null ? null : e.getCreated().getTimeInMillis(),
                        e.getSender(),
                        e.getMessage(),
                        e.getRead(),
                        e.getDirection()
                ))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/sms")
    public void deleteSms(@RequestBody SmsDto sms) {
        log.info("Delete sms id=" + sms.id);
        phoneService.deleteSms(sms.id);
    }

    @PostMapping("/sms")
    public void sendSms(@RequestBody SmsDto sms) {
        log.info("Send sms to {} '{}'", sms.number, sms.message);
        phoneService.sendSms(sms.number, sms.message);
    }

    @PostMapping("/sms/setRead")
    public void markSmsAsRead(@RequestBody SmsDto sms) {
        log.info("Mark sms id={} as read", sms.id);
        phoneService.markSmsAsRead(sms.id);
    }

}
