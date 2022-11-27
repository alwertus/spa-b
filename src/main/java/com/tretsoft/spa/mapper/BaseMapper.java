package com.tretsoft.spa.mapper;

import java.util.List;

public interface BaseMapper<SOURCE, DESTINATION> {

    DESTINATION sourceToDto(SOURCE source);

    SOURCE dtoToSource(DESTINATION dto);

    List<DESTINATION> sourcesToDtos(List<SOURCE> sources);

    List<SOURCE> dtosToSources(List<DESTINATION> sources);

}