package com.tretsoft.spa.web.controller.doings;

import com.tretsoft.spa.model.doings.DoLabel;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.doings.DoLabelService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.doings.DoLabelDto;
import com.tretsoft.spa.web.mapper.doings.DoLabelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doings-label")
public class DoLabelController extends CrudController<DoLabel, DoLabelDto> {


    public DoLabelController(DoLabelService service, DoLabelMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }
}
