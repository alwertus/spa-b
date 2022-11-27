package com.tretsoft.spa.controller;

import com.tretsoft.spa.exception.BaseException;
import com.tretsoft.spa.model.dto.ResponseError;
import com.tretsoft.spa.service.UserService;
import com.tretsoft.spa.model.dto.SpaUserDto;
import com.tretsoft.spa.validator.SpaUserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User operations")
public class UserController {

    private final UserService userService;
    private final SpaUserValidator userValidator;

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

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(BaseException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(ex));
    }




//    -----------------------------------

    @Operation(summary = "koko GET")
    @GetMapping
    public String testMethod1() {
        log.info("Input method GET");
        return "TEST METHOD - GET";
    }

    @Operation(summary = "koko POST")
    @PostMapping
    public String testMethod2() {
        log.info("Input method POST");
        return "TEST METHOD - POST";
    }

//    @Parameter(description = "Фильтрация по нназванию судна") @Nullable @RequestParam("name") String name,

}
