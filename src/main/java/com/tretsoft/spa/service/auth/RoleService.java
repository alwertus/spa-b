package com.tretsoft.spa.service.auth;

import com.tretsoft.spa.exception.NotFoundException;
import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<SpaRole> getAll() {
        return roleRepository.findAll();
    }

    public void addDefaultRoles(SpaUser user) {
        List<SpaRole> newRoles = roleRepository.findByIsDefaultIsTrue();
        if (user.getRoles() != null)
            newRoles.addAll(user.getRoles());
        user.setRoles(newRoles);
    }

    public SpaRole createRole(String name, boolean isDefault, Integer priority) {
        Optional<SpaRole> role = roleRepository.findByName(name);

        if (role.isPresent()) {
            log.warn("Role " + name + " already exists");
            return role.get();
        } else {
            log.info("Create " + (isDefault ? "default " : "") + "role: " + name);
        }

        SpaRole newRole = SpaRole.builder()
                .created(Calendar.getInstance())
                .name(name)
                .isDefault(isDefault)
                .priority(priority)
                .build();
        return roleRepository.save(newRole);
    }

    public SpaRole getByName(String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException(name));
    }

    public long getRecordsCount() {
        return roleRepository.count();
    }
}
