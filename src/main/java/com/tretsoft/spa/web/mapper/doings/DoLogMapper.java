package com.tretsoft.spa.web.mapper.doings;

import com.tretsoft.spa.model.doings.DoLog;
import com.tretsoft.spa.web.dto.doings.DoLogDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.Calendar;

@Mapper(componentModel = "spring")
public interface DoLogMapper extends BaseMapper<DoLog, DoLogDto> {

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

}
