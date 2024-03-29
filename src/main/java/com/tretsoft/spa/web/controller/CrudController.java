package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.exception.MethodNotSupportedException;
import com.tretsoft.spa.service.CrudService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.web.dto.ResponseDeleted;
import com.tretsoft.spa.web.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Validated
@RequiredArgsConstructor
public abstract class CrudController<POJO, DTO> extends ExceptionHandlerController {
    public final CrudService<POJO> service;
    public final BaseMapper<POJO, DTO> mapper;
    public final AuthenticationService authenticationService;

    protected void logInfo(String message) {
        log.info("[" + authenticationService.getCurrentUser().getLogin() + "]: " + this.getClass().getSimpleName() + ". " + message);
    }

    @GetMapping("/{id}")
    public DTO getById(@PathVariable Long id) {
        logInfo("Get by id=" + id);
        DTO result = mapper.sourceToDto(service.getById(id));
        if (result == null) {
            throw new MethodNotSupportedException();
        }
        return result;
    }

    @GetMapping
    public List<DTO> getAll() {
        logInfo("GetAll");
        List<DTO> result = mapper.sourcesToDtos(service.getAll());
        if (result == null) {
            throw new MethodNotSupportedException();
        }
        return result;
    }

    @PostMapping
    public DTO create(@Validated @RequestBody DTO dto) {
        logInfo("Create dto=" + dto);
        DTO result = mapper.sourceToDto(service.create(mapper.dtoToSource(dto)));
        if (result == null) {
            throw new MethodNotSupportedException();
        }
        return result;
    }

    @PutMapping
    public DTO update(@Validated @RequestBody DTO dto) {
        logInfo("Update dto=" + dto);
        DTO result = mapper.sourceToDto(service.update(mapper.dtoToSource(dto)));
        if (result == null) {
            throw new MethodNotSupportedException();
        }
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseDeleted delete(@PathVariable Long id) {
        logInfo("Delete id=" + id);
        service.delete(id);
        return new ResponseDeleted();
    }

    public String getUrl() {
        RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            return requestMapping.value()[0];
        }
        return "";
    }

}
