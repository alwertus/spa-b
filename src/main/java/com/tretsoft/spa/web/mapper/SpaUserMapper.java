package com.tretsoft.spa.web.mapper;

import com.tretsoft.spa.model.SpaRole;
import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.web.dto.SpaUserDto;
import org.mapstruct.Mapper;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SpaUserMapper extends BaseMapper<SpaUser, SpaUserDto> {

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

    default List<SpaRole> map(List<String> roleNames) {
        return Collections.emptyList();
    }

    @Override
    default SpaUserDto sourceToDto(SpaUser spaUser) {
        return SpaUserDto
                .builder()
                .login(spaUser.getLogin())
                .email(spaUser.getEmail())
                .created(spaUser.getCreated() == null ? null : spaUser.getCreated().getTimeInMillis())
                .roles(spaUser.getRoles().stream().map(SpaRole::getName).collect(Collectors.toList()))
//                .updated(spaUser.getUpdated() == null ? null : spaUser.getUpdated().getTimeInMillis())
                .build();
    }
}
