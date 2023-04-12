package com.tretsoft.spa.web.mapper.doings;

import com.tretsoft.spa.model.doings.DoLabel;
import com.tretsoft.spa.web.dto.doings.DoLabelDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoLabelMapper extends BaseMapper<DoLabel, DoLabelDto> {
}
