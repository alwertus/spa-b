package com.tretsoft.spa.web.mapper;

import java.util.List;

public interface BaseMapper<SOURCE, DTO> {

    DTO sourceToDto(SOURCE source);

    SOURCE dtoToSource(DTO dto);

    List<DTO> sourcesToDtos(List<SOURCE> sources);

    List<SOURCE> dtosToSources(List<DTO> sources);

}