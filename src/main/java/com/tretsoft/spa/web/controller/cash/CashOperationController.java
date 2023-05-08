package com.tretsoft.spa.web.controller.cash;

import com.tretsoft.spa.model.cash.CashOperation;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.cash.CashOperationService;
import com.tretsoft.spa.service.utility.DateService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.cash.CashOperationDto;
import com.tretsoft.spa.web.mapper.cash.CashOperationMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cash-operation")
public class CashOperationController extends CrudController<CashOperation, CashOperationDto> {
    public CashOperationController(CashOperationService service,
                                   CashOperationMapper mapper,
                                   AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }

    @GetMapping("/period")
    public List<CashOperationDto> getByPeriod(@RequestParam(name = "start") Long start,
                                              @RequestParam(name = "end") Long end) {
        logInfo(String.format("GetByPeriod {%s, %s}", start, end));

        CashOperationService cashOperationService = (CashOperationService) service;

        if (start == 0 || end == 0) {
            return getAll();
        }

        return mapper.sourcesToDtos(cashOperationService.getAllByInterval(
                DateService.getCalendarByMillis(start),
                DateService.getCalendarByMillis(end))
        );
    }

}
