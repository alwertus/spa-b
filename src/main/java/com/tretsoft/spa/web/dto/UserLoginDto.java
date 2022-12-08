package com.tretsoft.spa.web.dto;

public record UserLoginDto(SpaUserDto userData, String token, String refreshToken) {
}
