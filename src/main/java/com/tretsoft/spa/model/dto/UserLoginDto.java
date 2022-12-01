package com.tretsoft.spa.model.dto;

public record UserLoginDto(SpaUserDto userData, String token, String refreshToken) {
}
