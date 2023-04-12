package com.tretsoft.spa.web.mapper.doings;

import com.tretsoft.spa.model.doings.DoTask;
import com.tretsoft.spa.web.dto.doings.DoTaskDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.Calendar;

@Mapper(componentModel = "spring")
public interface DoTaskMapper extends BaseMapper<DoTask, DoTaskDto> {

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
