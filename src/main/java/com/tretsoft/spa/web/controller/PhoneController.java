package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.exception.BaseException;
import com.tretsoft.spa.service.PhoneService;
import com.tretsoft.spa.web.dto.ResponseError;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@Tag(name = "Phone", description = "Access to 3g modem")
public class PhoneController {
    private final PhoneService phoneService;

    private record SmsDto(Long id, Long created, String sender, String message){}

    @GetMapping("/sms")
    public List<SmsDto> getSms() {
        return phoneService
                .getSms()
                .stream()
                .map(e -> new SmsDto(e.getId(), e.getCreated().getTimeInMillis(), e.getSender(), e.getMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(BaseException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(ex));
    }

}
