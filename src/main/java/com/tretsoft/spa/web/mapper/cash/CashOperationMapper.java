package com.tretsoft.spa.web.mapper.cash;

import com.tretsoft.spa.model.cash.CashOperation;
import com.tretsoft.spa.web.dto.cash.CashOperationDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.Calendar;

@Mapper(componentModel = "spring")
public interface CashOperationMapper extends BaseMapper<CashOperation, CashOperationDto> {

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
