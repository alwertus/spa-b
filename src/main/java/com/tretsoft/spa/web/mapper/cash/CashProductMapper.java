package com.tretsoft.spa.web.mapper.cash;

import com.tretsoft.spa.model.cash.CashProduct;
import com.tretsoft.spa.web.dto.cash.CashProductDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashProductMapper extends BaseMapper<CashProduct, CashProductDto> {
}
