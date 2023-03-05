package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.model.doings.DoLog;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.doings.DoLogService;
import com.tretsoft.spa.web.dto.DoLogDto;
import com.tretsoft.spa.web.mapper.DoLogMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doings-log")
public class DoLogController extends CrudController<DoLog, DoLogDto> {
    public DoLogController(DoLogService service, DoLogMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }
}
