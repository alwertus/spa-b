package com.tretsoft.spa.web.mapper.admin;

import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.web.dto.admin.AdminUserDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdminUserMapper extends BaseMapper<SpaUser, AdminUserDto> {

    /** Calendar -> millis */
    default Long map(Calendar value) {
        return value == null ? null : value.getTimeInMillis();
    }

    /** millis -> Calendar */
    default Calendar map(Long value) {
        if (value == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(value);
        return c;
    }

    default List<SpaRole> map(List<String> ignoredRoleNames) {
        return Collections.emptyList();
    }

    @Override
    default AdminUserDto sourceToDto(SpaUser spaUser) {
        return AdminUserDto
                .builder()
                .login(spaUser.getLogin())
                .email(spaUser.getEmail())
                .created(spaUser.getCreated() == null ? null : spaUser.getCreated().getTimeInMillis())
                .lastLogin(spaUser.getLastLogin() == null ? null : spaUser.getLastLogin().getTimeInMillis())
                .roles(spaUser.getRoles().stream().map(SpaRole::getName).collect(Collectors.toList()))
                .build();
    }
}
