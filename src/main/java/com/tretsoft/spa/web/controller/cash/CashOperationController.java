package com.tretsoft.spa.web.controller.cash;

import com.tretsoft.spa.model.cash.CashOperation;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.cash.CashOperationService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.cash.CashOperationDto;
import com.tretsoft.spa.web.mapper.cash.CashOperationMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cash-operation")
public class CashOperationController extends CrudController<CashOperation, CashOperationDto> {
    public CashOperationController(CashOperationService service, CashOperationMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }


}
