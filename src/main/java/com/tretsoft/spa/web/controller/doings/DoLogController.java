package com.tretsoft.spa.web.controller.doings;

import com.tretsoft.spa.model.doings.DoLog;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.doings.DoLogService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.doings.DoLogDto;
import com.tretsoft.spa.web.mapper.doings.DoLogMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/doings-log")
public class DoLogController extends CrudController<DoLog, DoLogDto> {
    public DoLogController(DoLogService service, DoLogMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }

    @GetMapping("/period")
    public List<DoLogDto> getByPeriod(@RequestParam(name = "start") Long start,
                                      @RequestParam(name = "end") Long end) {
        logInfo(String.format("GetByPeriod {%s, %s}", start, end));

        DoLogService doLogService = (DoLogService) service;

        if (start == 0 || end == 0) {
            return getAll();
        }

        Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endDate.setTimeInMillis(end);

        return mapper.sourcesToDtos(doLogService.getAllByInterval(startDate, endDate));
    }
}
