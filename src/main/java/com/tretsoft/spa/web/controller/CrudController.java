package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.service.CurdService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.web.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public abstract class CrudController<POJO, DTO> extends ExceptionHandlerController {
    public final CurdService<POJO> service;
    public final BaseMapper<POJO, DTO> mapper;
    public final AuthenticationService authenticationService;

    protected void logInfo(String message) {
        log.info("[" + authenticationService.getCurrentUser().getLogin() + "]: " + this.getClass().getSimpleName() + ". " + message);
    }

    @GetMapping("/{id}")
    public DTO getById(@PathVariable Long id) {
        logInfo("Get by id=" + id);
        return mapper.sourceToDto(service.getById(id));
    }

    @GetMapping
    public List<DTO> getAll() {
        logInfo("GetAll");
        return mapper.sourcesToDtos(service.getAll());
    }

    @PostMapping
    public DTO create(@RequestBody DTO dto) {
        logInfo("Create dto=" + dto);
        return mapper.sourceToDto(service.create(mapper.dtoToSource(dto)));
    }

    @PutMapping
    public DTO update(@RequestBody DTO dto) {
        logInfo("Update dto=" + dto);
        return mapper.sourceToDto(service.update(mapper.dtoToSource(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logInfo("Delete id=" + id);
        service.delete(id);
    }

}
