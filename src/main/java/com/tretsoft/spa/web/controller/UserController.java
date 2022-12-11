package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.exception.BaseException;
import com.tretsoft.spa.web.dto.ResponseError;
import com.tretsoft.spa.web.dto.UserLoginDto;
import com.tretsoft.spa.service.AuthenticationService;
import com.tretsoft.spa.service.UserService;
import com.tretsoft.spa.web.dto.SpaUserDto;
import com.tretsoft.spa.web.validator.SpaUserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User operations")
public class UserController {

    private final UserService userService;
    private final SpaUserValidator userValidator;
    private final AuthenticationService authenticationService;

    @InitBinder
    public void initValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidator);
    }

    @Operation(summary = "Register new user")
    @PutMapping
    public SpaUserDto register(
            @Valid
            @Parameter(description = "Fields from registration form")
            @RequestBody SpaUserDto dto
    ) {
        return userService.create(dto);
    }

    @Operation(summary = "Email confirmation")
    @GetMapping("/emailConfirm")
    public void emailConfirm(
            @Parameter(description = "Confirmation key that was sent by mail")
            @RequestParam("key") String key
    ) {
        userService.confirmEmail(key);
    }

    @Operation(summary = "Try to login, generate token")
    @PostMapping("/login")
    public UserLoginDto signIn(
            @Valid
            @Parameter(description = "use Login and Password attributes to sing in")
            @RequestBody SpaUserDto inDto
    ) {
        return authenticationService.signInByLoginAndPassword(inDto.getLogin(), inDto.getPassword());
    }

    @Operation(summary = "Update tokens")
    @PostMapping("/update-token")
    public UserLoginDto updateToken() {
        return authenticationService.updateTokens();

    }

    @Operation(summary = "Get user list")
    @GetMapping
    public List<String> getUserList() {
        return userService.getAllUsers();
    }


    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(BaseException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(ex));
    }

//    @Parameter(description = "Фильтрация по нназванию судна") @Nullable @RequestParam("name") String name,

}
