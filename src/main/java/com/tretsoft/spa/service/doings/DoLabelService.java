package com.tretsoft.spa.service.doings;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.exception.NullAttributeException;
import com.tretsoft.spa.model.doings.DoLabel;
import com.tretsoft.spa.repository.DoLabelRepository;
import com.tretsoft.spa.service.CurdService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class DoLabelService implements CurdService<DoLabel> {

    private final DoLabelRepository doLabelRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<DoLabel> getAll() {
        return doLabelRepository.findAllByUser(authenticationService.getCurrentUser());
    }

    private void checkAccess(DoLabel label) {
        if (!label.getUser().equals(authenticationService.getCurrentUser()))
            throw new ForbiddenException("Label id=" + label.getId());
    }

    @Override
    public DoLabel create(DoLabel obj) {
        obj.setUser(authenticationService.getCurrentUser());
        return doLabelRepository.save(obj);
    }

    @Override
    public DoLabel update(DoLabel obj) {
        if (obj.getId() == null) {
            throw new NullAttributeException("id");
        }

        DoLabel label = doLabelRepository
                .findById(obj.getId())
                .orElseThrow(() -> new BadRequestException("Label id=" + obj.getId() + " not found"));

        checkAccess(label);

        if (obj.getName() != null)
            label.setName(obj.getName());

        label.setColor(obj.getColor());

        return doLabelRepository.save(label);
    }

    @Override
    public void delete(Long id) {

    }
}
