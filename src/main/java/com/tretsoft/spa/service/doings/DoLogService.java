package com.tretsoft.spa.service.doings;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.MethodNotSupportedException;
import com.tretsoft.spa.model.doings.DoLog;
import com.tretsoft.spa.model.doings.DoTask;
import com.tretsoft.spa.repository.DoLogRepository;
import com.tretsoft.spa.service.CurdService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class DoLogService implements CurdService<DoLog> {

    private final DoLogRepository doLogRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<DoLog> getAll() {
        return doLogRepository.findAllByUser(authenticationService.getCurrentUser());
    }

    @Override
    public DoLog getById(Long id) {
        return doLogRepository.findById(id).orElseThrow(() -> new BadRequestException("Id: " + id + " not found"));
    }

    public List<DoLog> getAllByInterval(Calendar startDate, Calendar endDate) {
        return doLogRepository.findByUserAndIntervalBetween(authenticationService.getCurrentUser(), startDate, endDate);
    }

    @Override
    public DoLog create(DoLog obj) {
        log.info("[" + authenticationService.getCurrentUser().getLogin() + "]: Create log: " + obj);
        return doLogRepository.save(obj);
    }

    @Override
    public DoLog update(DoLog obj) {
        throw new MethodNotSupportedException();
    }

    @Override
    public void delete(Long id) {
        throw new MethodNotSupportedException();
    }

    public void deleteAllByTask(DoTask task) {
        doLogRepository.deleteAllByTask(task);
    }
}
