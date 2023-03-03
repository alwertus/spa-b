package com.tretsoft.spa.web.mapper;

import com.tretsoft.spa.model.doings.DoLabel;
import com.tretsoft.spa.web.dto.DoLabelDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoLabelMapper extends BaseMapper<DoLabel, DoLabelDto> {
}
