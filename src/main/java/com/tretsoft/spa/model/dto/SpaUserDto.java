package com.tretsoft.spa.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpaUserDto {
    @NotNull private String login;

    @NotNull private String password;
    private String email;
    private Long created;
    private Long updated;
    private String message;

}