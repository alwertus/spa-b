package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.service.CurdService;
import com.tretsoft.spa.web.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public abstract class CrudController<POJO, DTO> extends ExceptionHandlerController {
    final CurdService<POJO> service;
    final BaseMapper<POJO, DTO> mapper;

    @GetMapping
    public List<DTO> getAll() {
        return mapper.sourcesToDtos(service.getAll());
    }

    @PostMapping
    public DTO create(@RequestBody DTO dto) {
        return mapper.sourceToDto(service.create(mapper.dtoToSource(dto)));
    }

    @PutMapping
    public DTO update(@RequestBody DTO dto) {
        return mapper.sourceToDto(service.update(mapper.dtoToSource(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
