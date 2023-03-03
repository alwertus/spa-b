package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.model.doings.DoTask;
import com.tretsoft.spa.service.doings.DoTaskService;
import com.tretsoft.spa.web.dto.DoTaskDto;
import com.tretsoft.spa.web.mapper.DoTaskMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("doings-task")
public class DoTaskController extends CrudController<DoTask, DoTaskDto> {

    public DoTaskController(DoTaskService service, DoTaskMapper mapper) {
        super(service, mapper);
    }
}
