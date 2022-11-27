package com.tretsoft.spa.mapper;

import com.tretsoft.spa.model.domain.SpaUser;
import com.tretsoft.spa.model.dto.SpaUserDto;
import org.mapstruct.Mapper;

import java.util.Calendar;

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

    @Override
    default SpaUserDto sourceToDto(SpaUser spaUser) {
        return SpaUserDto
                .builder()
                .login(spaUser.getLogin())
                .email(spaUser.getEmail())
                .created(spaUser.getCreated() == null ? null : spaUser.getCreated().getTimeInMillis())
                .updated(spaUser.getUpdated() == null ? null : spaUser.getUpdated().getTimeInMillis())
                .build();
    }
}
