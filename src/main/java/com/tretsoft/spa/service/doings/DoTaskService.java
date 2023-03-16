package com.tretsoft.spa.service.doings;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.model.doings.DoLabel;
import com.tretsoft.spa.model.doings.DoLog;
import com.tretsoft.spa.model.doings.DoTask;
import com.tretsoft.spa.repository.DoTaskRepository;
import com.tretsoft.spa.service.CurdService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class DoTaskService implements CurdService<DoTask> {

    private final DoTaskRepository doTaskRepository;
    private final AuthenticationService authenticationService;
    private final DoLabelService doLabelService;
    private final DoLogService doLogService;

    @Override
    public List<DoTask> getAll() {
        return doTaskRepository.findAllByUser(authenticationService.getCurrentUser());
    }

    @Override
    public DoTask update(DoTask obj) {
        SpaUser user = authenticationService.getCurrentUser();
        DoTask task = doTaskRepository
                .findById(obj.getId())
                .orElseThrow(() -> new BadRequestException("id=" + obj.getId()));

        if (!task.getUser().equals(user)) {
            throw new ForbiddenException("Task id=" + obj.getId());
        }

        // if task was active and now inactive -> create log record
        if (obj.getStartDate() == null && task.getStartDate() != null) {
            doLogService.create(DoLog
                    .builder()
                    .startDate(task.getStartDate())
                    .endDate(Calendar.getInstance())
                    .task(task)
                    .build());
        }

        task.setChecked(obj.getChecked());
        // TODO: if set checked = false - set startDate = null
        task.setName(obj.getName());
        task.setStartDate(obj.getStartDate());
        final List<DoLabel> LABELS = task.getLabels();

        if (obj.getLabels() != null) {
            // new labels that has id
            List<DoLabel> labelsToAdd = obj.getLabels()
                    .stream()
                    .filter(e -> !LABELS.contains(e) && e.getId() != null)
                    .toList();

            // new labels that need to create
            List<DoLabel> createdLabelsToAdd = obj.getLabels()
                    .stream()
                    .filter(e -> e.getId() == null)
                    .map(e -> doLabelService.create(
                            DoLabel.builder()
                                    .user(user)
                                    .name(e.getName())
                                    .color(e.getColor()).build()))
                    .toList();

            List<DoLabel> labelsToRemove = task.getLabels().stream().filter(e -> !obj.getLabels().contains(e)).toList();

            log.trace("Labels to add: " + labelsToAdd);
            for (DoLabel label : labelsToAdd)
                task.getLabels().add(label);

            log.trace("Labels to add(created): " + createdLabelsToAdd);
            for (DoLabel label : createdLabelsToAdd)
                task.getLabels().add(label);

            log.trace("Labels to remove: " + labelsToRemove);
            for (DoLabel label : labelsToRemove)
                task.getLabels().remove(label);
        }

        return doTaskRepository.saveAndFlush(task);
    }

    @Override
    public DoTask create(DoTask obj) {
        SpaUser user = authenticationService.getCurrentUser();

        obj.setUser(user);
        if (obj.getChecked() == null) {
            obj.setChecked(true);
        }

        if (obj.getLabels() != null) {
            // new labels that need to create
            List<DoLabel> labelsForCreate = obj.getLabels()
                    .stream()
                    .filter(e -> e.getId() == null)
                    .map(e -> doLabelService.create(
                            DoLabel.builder()
                                    .user(user)
                                    .name(e.getName())
                                    .color(e.getColor()).build()))
                    .toList();

            obj.getLabels().removeAll(obj.getLabels()
                    .stream()
                    .filter(e -> e.getId() == null)
                    .toList());

            log.trace("Labels to add(created): " + labelsForCreate);
            for (DoLabel label : labelsForCreate)
                obj.getLabels().add(label);
        }

        return doTaskRepository.save(obj);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        DoTask task = doTaskRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("id=" + id));

        if (!task.getUser().equals(authenticationService.getCurrentUser()))
            throw new ForbiddenException("Task id=" + id);

        doLogService.deleteAllByTask(task);
        doTaskRepository.delete(task);
    }
}
