package com.tretsoft.spa.web.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleDto {

    @NonNull
    private String userLogin;

    @NonNull
    private String roleName;

}
