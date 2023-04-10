package com.tretsoft.spa.web.mapper;

import com.tretsoft.spa.model.cash.Currency;
import com.tretsoft.spa.web.dto.CurrencyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper extends BaseMapper<Currency, CurrencyDto> {
}
