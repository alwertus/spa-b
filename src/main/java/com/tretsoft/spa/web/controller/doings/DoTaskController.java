package com.tretsoft.spa.web.controller.doings;

import com.tretsoft.spa.model.doings.DoTask;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.doings.DoTaskService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.doings.DoTaskDto;
import com.tretsoft.spa.web.mapper.doings.DoTaskMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/doings-task")
public class DoTaskController extends CrudController<DoTask, DoTaskDto> {

    public DoTaskController(DoTaskService service, DoTaskMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }
}
