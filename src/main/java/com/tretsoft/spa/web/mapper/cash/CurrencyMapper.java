package com.tretsoft.spa.web.mapper.cash;

import com.tretsoft.spa.model.cash.Currency;
import com.tretsoft.spa.web.dto.cash.CurrencyDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper extends BaseMapper<Currency, CurrencyDto> {
}
