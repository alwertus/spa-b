package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@Tag(name = "Phone", description = "Access to 3g modem")
public class PhoneController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public List<String> getAll() {
//        log.info(authenticationService.getCurrentUser().getRoles().stream().map(SpaRole::getName).collect(Collectors.toList()));
        return List.of("FIRST", "SECOND"/*, authenticationService.getCurrentUser().getLogin()*/);
    }

    @GetMapping("/public")
    public List<String> pub() {
        return List.of("PUB_1", "PUB_2", "PUB_3", "PUB_4", "PUB_5");
    }

    @GetMapping("/private")
    public List<String> priv() {
        return List.of("PUB_1", "PUB_2", "PUB_3", "PUB_4", "PUB_5", "ADMIN_0", "ADMIN_1");
    }

}
